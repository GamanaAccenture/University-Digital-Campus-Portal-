(function () {
    "use strict";

    function parseAuthoredDate(value) {
        const match = /^(\d{4})-(\d{2})-(\d{2})/.exec(value || "");
        if (!match) {
            return null;
        }

        const year = Number(match[1]);
        const month = Number(match[2]) - 1;
        const day = Number(match[3]);
        const date = new Date(year, month, day);
        if (date.getFullYear() !== year || date.getMonth() !== month || date.getDate() !== day) {
            return null;
        }

        date.setHours(0, 0, 0, 0);
        return date;
    }

    function updateNotificationStatuses() {
        const today = new Date();
        today.setHours(0, 0, 0, 0);

        Array.prototype.forEach.call(
            document.querySelectorAll("[data-notification-status]"),
            function (element) {
                const openingValue = element.getAttribute("data-opening-date") || "";
                const closingValue = element.getAttribute("data-closing-date") || "";
                const openingDate = parseAuthoredDate(openingValue);
                const closingDate = parseAuthoredDate(closingValue);
                let state;
                let label;

                if ((openingValue && !openingDate) || (closingValue && !closingDate) ||
                    (openingDate && closingDate && openingDate > closingDate)) {
                    state = "unknown";
                    label = "Status unavailable";
                } else if (openingDate && today < openingDate) {
                    state = "upcoming";
                    label = "Upcoming";
                } else if (closingDate && today > closingDate) {
                    state = "closed";
                    label = "Closed";
                } else {
                    state = "open";
                    label = "Open";
                }

                element.dataset.state = state;
                element.textContent = label;
                element.setAttribute("aria-label", "Status: " + label);
            }
        );
    }

    if (document.readyState === "loading") {
        document.addEventListener("DOMContentLoaded", updateNotificationStatuses);
    } else {
        updateNotificationStatuses();
    }
}());
