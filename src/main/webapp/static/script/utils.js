// import superagent from 'superagent';
import { setOldPoints } from "./graph.js";
import {getData} from "./connector.js";


// window.resizeIframe = resizeIframe;
window.clearHistory = clearHistory;
window.showHeader = showHeader;
// export function resizeIframe(iframe) {
//     iframe.style.height = '300px';
//     iframe.style.height = iframe.contentWindow.document.body.scrollHeight+20 + 'px';
//     iframe.contentWindow.clearHistory = clearHistory;
//     iframe.parentElement.hidden = false;
// }

// yes, it would be better to use GET not POST in a bunch of situations, but the task said POST
export function ajax(formData, successFunc, errorFunc = ajaxProblem) {
    window.superagent
        .post('/webLab-2.0/stuff')
        .send(formData)
        .end((err,res) => {
            if  (err || !res.ok) {
                errorFunc(err)
            } else {
                successFunc(res.text, res);
            }
        })
}
function ajaxProblem() {
    alert('oh no, ajax problem');
}

function clearHistory() {
    const formData = new FormData();
    formData.append('clearHistory','true');
    ajax(formData, function() {
        let resFrame = document.getElementById('result').contentWindow.document;
        let history = resFrame.getElementById('history');
        history.remove();
        setOldPoints([]);
    })
}

// don't have time to implement a normal solution
function showHeader(checkbox) {
    let header = document.getElementById('header');
    if (checkbox.checked) {
        header.className = 'blob header header-active';
    } else {
        header.className = 'blob header';
    }
}

export function sendShootingReq(x,y,r) {
    const formData = new FormData();
    formData.append('x',x);
    formData.append('y',y);
    formData.append('r',r);
    formData.append('shoot','true');
    ajax(formData, function(responseText, response) {
        console.log(responseText, window.location.pathname);
        window.location.pathname = responseText
        // let resFrame = document.getElementById('result');
        // let res = resFrame.contentWindow.document;
        // res.open();
        // res.write(responseText);
        // res.close();
        // resizeIframe(resFrame);
    })
}

export function reload() {
    getData();
}