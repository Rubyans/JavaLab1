function fetchData(endpoint) {
    fetch(endpoint)
        .then(response => response.text())
        .then(data => alert("Operation successful!"))
        .catch(error => alert("Error: " + error));
}

function downloadExcel() {
    window.location.href = '/getExel';
}
