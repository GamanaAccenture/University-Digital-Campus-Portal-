// Replace these placeholders with the endpoint and relative path for your all-courses query.
const GRAPHQL_ENDPOINT = "http://localhost:4502/graphql/execute.json/udcp";
const ALL_COURSES_QUERY_PATH = "http://localhost:4502/graphql/execute.json/udcp/couresesreturn";
const COURSES_BY_SCHOOL_QUERY_PATH = "http://localhost:4502/graphql/execute.json/udcp/courses-by-school";
const SCHOOL_VARIABLE_NAME = "school";
const COURSE_REQUEST_TIMEOUT_MS = 20000;
const SCHOOL_FILTERS = {
    engineering: "Engineering",
    management: "Management",
    design: "Design"
};

function isCourse(value) {
    return value !== null &&
        typeof value === "object" &&
        !Array.isArray(value) &&
        Object.prototype.hasOwnProperty.call(value, "courseName");
}

function findCourses(value) {
    if (Array.isArray(value)) {
        const courses = [];
        for (let index = 0; index < value.length; index += 1) {
            if (isCourse(value[index])) {
                courses.push(value[index]);
            }
        }
        if (courses.length) {
            return courses;
        }

        for (let index = 0; index < value.length; index += 1) {
            const nestedCourses = findCourses(value[index]);
            if (nestedCourses.length) {
                return nestedCourses;
            }
        }
    } else if (value && typeof value === "object") {
        if (isCourse(value)) {
            return [value];
        }

        const keys = Object.keys(value);
        for (let index = 0; index < keys.length; index += 1) {
            const nestedCourses = findCourses(value[keys[index]]);
            if (nestedCourses.length) {
                return nestedCourses;
            }
        }
    }

    return [];
}

function displayValue(value) {
    if (value === null || typeof value === "undefined" || value === "") {
        return "Not provided";
    }

    if (Array.isArray(value)) {
        return value.map(displayValue).join(", ");
    }

    if (typeof value === "object") {
        const plainTextKeys = ["plaintext", "plainText", "text", "value", "title", "name"];
        for (let index = 0; index < plainTextKeys.length; index += 1) {
            const key = plainTextKeys[index];
            if (value[key] !== null && typeof value[key] !== "undefined") {
                return displayValue(value[key]);
            }
        }
        return JSON.stringify(value);
    }

    return String(value);
}

function appendDetail(details, label, value) {
    const item = document.createElement("div");
    item.className = "course-fragment-list__detail";

    const term = document.createElement("dt");
    term.textContent = label;
    const description = document.createElement("dd");
    description.textContent = displayValue(value);

    item.appendChild(term);
    item.appendChild(description);
    details.appendChild(item);
}

function createCourseCard(course) {
    const card = document.createElement("article");
    card.className = "course-fragment-list__card";

    const title = document.createElement("h3");
    title.className = "course-fragment-list__course-name";
    title.textContent = displayValue(course.courseName);
    card.appendChild(title);

    const school = document.createElement("p");
    school.className = "course-fragment-list__school";
    school.textContent = "School: " + displayValue(course.school);
    card.appendChild(school);

    const description = document.createElement("p");
    description.className = "course-fragment-list__description";
    description.textContent = displayValue(course.description);
    card.appendChild(description);

    const details = document.createElement("dl");
    details.className = "course-fragment-list__details";
    appendDetail(details, "Duration", course.duration);
    appendDetail(details, "Fees", course.fees);
    appendDetail(details, "Eligibility", course.eligibility);
    card.appendChild(details);

    return card;
}

function initializeCourseFragmentList(root) {
    const status = root.querySelector("[data-course-status]");
    const grid = root.querySelector("[data-course-grid]");
    const filters = root.querySelectorAll("[data-course-filter]");
    let activeRequest = 0;

    function setStatus(message, state) {
        status.dataset.state = state;
        status.textContent = message;
        status.hidden = !message;
    }

    function setActiveFilter(selectedFilter) {
        Array.prototype.forEach.call(filters, function (filter) {
            const isActive = filter === selectedFilter;
            filter.classList.toggle("is-active", isActive);
            filter.setAttribute("aria-pressed", String(isActive));
        });
    }

    function renderCourses(courses) {
        grid.textContent = "";
        grid.setAttribute("aria-busy", "false");
        if (!courses.length) {
            setStatus("No courses found for this filter.", "empty");
            return;
        }

        const cards = document.createDocumentFragment();
        courses.forEach(function (course) {
            cards.appendChild(createCourseCard(course));
        });
        grid.appendChild(cards);
        setStatus("", "ready");
    }

    function loadCourses(category) {
        const requestNumber = activeRequest + 1;
        activeRequest = requestNumber;
        setStatus("Loading courses...", "loading");
        grid.setAttribute("aria-busy", "true");

        const school = SCHOOL_FILTERS[category];
        const persistedQueryPath = school ? COURSES_BY_SCHOOL_QUERY_PATH : ALL_COURSES_QUERY_PATH;
        if (!GRAPHQL_ENDPOINT || GRAPHQL_ENDPOINT.indexOf("REPLACE_WITH_") === 0 ||
            !persistedQueryPath || persistedQueryPath.indexOf("REPLACE_WITH_") === 0) {
            grid.setAttribute("aria-busy", "false");
            setStatus(
                "Add your GraphQL endpoint and persisted-query path in _course-fragment-list.js.",
                "error"
            );
            return;
        }

        const endpoint = GRAPHQL_ENDPOINT.replace(/\/+$/, "");
        const queryUrl = /^https?:\/\//i.test(persistedQueryPath)
            ? persistedQueryPath
            : endpoint + "/" + persistedQueryPath.replace(/^\/+/, "");
        const schoolParameter = school
            ? ";" + encodeURIComponent(SCHOOL_VARIABLE_NAME) + "=" + encodeURIComponent(school)
            : "";
        const requestUrl = queryUrl + schoolParameter;
        const controller = new AbortController();
        const timeoutId = setTimeout(function () {
            controller.abort();
        }, COURSE_REQUEST_TIMEOUT_MS);

        fetch(requestUrl, {
            method: "GET",
            headers: {
                Accept: "application/json"
            },
            signal: controller.signal
        })
            .then(function (response) {
                if (!response.ok) {
                    throw new Error("Course request failed (HTTP " + response.status + ").");
                }

                const contentType = response.headers.get("content-type") || "";
                if (contentType && contentType.toLowerCase().indexOf("json") === -1) {
                    throw new Error(
                        "AEM returned " + contentType +
                        " instead of GraphQL JSON. Check your AEM login and persisted query URL."
                    );
                }

                return response.json();
            })
            .then(function (payload) {
                if (payload.errors && payload.errors.length) {
                    const messages = payload.errors.map(function (error) {
                        return error.message || "GraphQL request error";
                    });
                    throw new Error(messages.join("; "));
                }
                return findCourses(payload.data || payload);
            })
            .then(function (courses) {
                clearTimeout(timeoutId);
                if (requestNumber === activeRequest) {
                    renderCourses(courses);
                }
            })
            .catch(function (error) {
                clearTimeout(timeoutId);
                if (requestNumber === activeRequest) {
                    grid.setAttribute("aria-busy", "false");
                    const message = error.name === "AbortError"
                        ? "The GraphQL request timed out. Check that AEM is available and the query can be reached."
                        : error.message;
                    setStatus("Unable to load courses. " + message, "error");
                }
            });
    }

    Array.prototype.forEach.call(filters, function (filter) {
        filter.addEventListener("click", function () {
            setActiveFilter(filter);
            loadCourses(filter.getAttribute("data-course-filter"));
        });
    });

    loadCourses("all");
}

function startCourseFragmentLists() {
    const components = document.querySelectorAll("[data-course-fragment-list]");
    Array.prototype.forEach.call(components, initializeCourseFragmentList);
}

if (document.readyState === "loading") {
    document.addEventListener("DOMContentLoaded", startCourseFragmentLists);
} else {
    startCourseFragmentLists();
}
