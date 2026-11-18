// Set this to the complete URL of the existing all-courses persisted query.
const COURSES_QUERY_URL = "http://localhost:4502/graphql/execute.json/udcp/couresesreturn";
const COURSE_REQUEST_TIMEOUT_MS = 20000;

function isCourse(value) {
    return value !== null &&
        typeof value === "object" &&
        !Array.isArray(value) &&
        Object.prototype.hasOwnProperty.call(value, "courseName");
}

function findCourses(value) {
    if (Array.isArray(value)) {
        const courses = value.filter(isCourse);
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
        const textKeys = ["plaintext", "plainText", "text", "value", "title", "name"];
        for (let index = 0; index < textKeys.length; index += 1) {
            const key = textKeys[index];
            if (value[key] !== null && typeof value[key] !== "undefined") {
                return displayValue(value[key]);
            }
        }
        return JSON.stringify(value);
    }

    return String(value);
}

function setStatus(status, message, state) {
    status.dataset.state = state;
    status.textContent = message;
    status.hidden = !message;
}

function createOption(value, label) {
    const option = document.createElement("option");
    option.value = value;
    option.textContent = label;
    return option;
}

function createComparisonTable(firstCourse, secondCourse) {
    const table = document.createElement("table");
    table.className = "course-comparison__table";

    const caption = document.createElement("caption");
    caption.textContent = "Course comparison";
    table.appendChild(caption);

    const fields = [
        ["Course Name", "courseName"],
        ["School", "school"],
        ["Duration", "duration"],
        ["Fees", "fees"],
        ["Eligibility", "eligibility"],
        ["Description", "description"]
    ];

    const thead = document.createElement("thead");
    const headerRow = document.createElement("tr");
    const detailHeading = document.createElement("th");
    detailHeading.scope = "col";
    detailHeading.textContent = "Course detail";
    headerRow.appendChild(detailHeading);

    [firstCourse, secondCourse].forEach(function (course, index) {
        const heading = document.createElement("th");
        heading.scope = "col";
        heading.textContent = "Course " + (index + 1);
        headerRow.appendChild(heading);
    });
    thead.appendChild(headerRow);
    table.appendChild(thead);

    const tbody = document.createElement("tbody");
    fields.forEach(function (field) {
        const row = document.createElement("tr");
        const label = document.createElement("th");
        label.scope = "row";
        label.textContent = field[0];
        row.appendChild(label);

        [firstCourse, secondCourse].forEach(function (course) {
            const cell = document.createElement("td");
            cell.textContent = displayValue(course[field[1]]);
            row.appendChild(cell);
        });
        tbody.appendChild(row);
    });
    table.appendChild(tbody);

    return table;
}

function initializeCourseComparison(root) {
    const firstSelect = root.querySelector("[data-first-course]");
    const secondSelect = root.querySelector("[data-second-course]");
    const compareButton = root.querySelector("[data-compare-courses]");
    const status = root.querySelector("[data-course-status]");
    const result = root.querySelector("[data-comparison-result]");
    let courses = [];

    function updateSelections() {
        const firstValue = firstSelect.value;
        const secondValue = secondSelect.value;

        Array.prototype.forEach.call(secondSelect.options, function (option) {
            option.disabled = option.value !== "" && option.value === firstValue;
        });
        Array.prototype.forEach.call(firstSelect.options, function (option) {
            option.disabled = option.value !== "" && option.value === secondValue;
        });

        compareButton.disabled = firstValue === "" ||
            secondValue === "" ||
            firstValue === secondValue;
    }

    function loadCourses() {
        if (!COURSES_QUERY_URL || COURSES_QUERY_URL.indexOf("REPLACE_WITH_") === 0) {
            setStatus(
                status,
                "Add the existing all-courses persisted query URL to COURSES_QUERY_URL in course-comparison.js.",
                "error"
            );
            return;
        }

        setStatus(status, "Loading courses...", "loading");
        firstSelect.disabled = true;
        secondSelect.disabled = true;
        compareButton.disabled = true;
        result.setAttribute("aria-busy", "true");

        const controller = new AbortController();
        const timeoutId = setTimeout(function () {
            controller.abort();
        }, COURSE_REQUEST_TIMEOUT_MS);

        fetch(COURSES_QUERY_URL, {
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
                        " instead of GraphQL JSON. Check the persisted query URL and AEM login."
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
                if (!payload.data || typeof payload.data !== "object") {
                    throw new Error("The GraphQL response does not contain a data object.");
                }
                return findCourses(payload.data);
            })
            .then(function (loadedCourses) {
                clearTimeout(timeoutId);
                courses = loadedCourses;
                firstSelect.textContent = "";
                secondSelect.textContent = "";
                firstSelect.appendChild(createOption("", "Select a course"));
                secondSelect.appendChild(createOption("", "Select a course"));

                courses.forEach(function (course, index) {
                    const courseName = displayValue(course.courseName);
                    firstSelect.appendChild(createOption(String(index), courseName));
                    secondSelect.appendChild(createOption(String(index), courseName));
                });

                result.textContent = "";
                result.hidden = true;
                result.setAttribute("aria-busy", "false");
                firstSelect.disabled = courses.length === 0;
                secondSelect.disabled = courses.length === 0;

                if (!courses.length) {
                    compareButton.disabled = true;
                    setStatus(status, "No courses are available to compare.", "empty");
                    return;
                }

                updateSelections();
                setStatus(status, "", "ready");
            })
            .catch(function (error) {
                clearTimeout(timeoutId);
                firstSelect.disabled = true;
                secondSelect.disabled = true;
                compareButton.disabled = true;
                result.setAttribute("aria-busy", "false");
                const message = error.name === "AbortError"
                    ? "The GraphQL request timed out. Check that AEM is available."
                    : error.message;
                setStatus(status, "Unable to load courses. " + message, "error");
            });
    }

    firstSelect.addEventListener("change", updateSelections);
    secondSelect.addEventListener("change", updateSelections);
    compareButton.addEventListener("click", function () {
        const firstIndex = firstSelect.value;
        const secondIndex = secondSelect.value;
        if (firstIndex === "" || secondIndex === "" || firstIndex === secondIndex) {
            setStatus(status, "Select two different courses to compare.", "error");
            updateSelections();
            return;
        }

        result.textContent = "";
        result.appendChild(createComparisonTable(courses[Number(firstIndex)], courses[Number(secondIndex)]));
        result.hidden = false;
        result.setAttribute("aria-busy", "false");
        setStatus(status, "", "ready");
    });

    loadCourses();
}

function startCourseComparisons() {
    const components = document.querySelectorAll("[data-course-comparison]");
    Array.prototype.forEach.call(components, initializeCourseComparison);
}

if (document.readyState === "loading") {
    document.addEventListener("DOMContentLoaded", startCourseComparisons);
} else {
    startCourseComparisons();
}
