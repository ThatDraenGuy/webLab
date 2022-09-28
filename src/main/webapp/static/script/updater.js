import {ajax, reload} from "./utils.js";

window.submit = submit;
const form = document.getElementById("updater-form");
form.addEventListener("submit", submit)

function submit(e) {
    e.preventDefault();
    const choosers = document.getElementsByTagName("quadrant-chooser");
    for (const chooser of choosers) {
        chooser.setAttribute("checked", false+"");
    }
    let res = true;
    for (const chooser of choosers) {
        if (chooser.getAttribute("status")==="false") {
            res = false;
        }
        chooser.setAttribute("checked", true+"");
    }
    if (res) {
        let formData = new FormData(document.getElementById("updater-form"));
        ajax(formData, success, error);
    }
}

function success(resText) {
    reload();
}
function error(err) {
    alert("Insufficient quadrant data!");
}


