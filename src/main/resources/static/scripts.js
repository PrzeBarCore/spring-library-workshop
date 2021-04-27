function loadState() {
    let checkboxes = document.querySelectorAll('input[type=checkbox]');
    checkboxes.forEach(checkbox =>
        changeLabelAndInputFields(checkbox.id));
}

function showOrHide(idOfInputField) {
    if (document.getElementById(idOfInputField).checked) {
        document.getElementById('2Input-' + idOfInputField).style.display = "block";
        document.getElementById('1Input-' + idOfInputField).style.display = "none";

    } else {
        document.getElementById('1Input-' + idOfInputField).style.display = "block";
        document.getElementById('2Input-' + idOfInputField).style.display = "none";
    }
}

function changeLabelAndInputFields(idOfCheckbox) {
    let idOfLabel = document.getElementById('label-' + idOfCheckbox);
    let isChecked = document.getElementById(idOfCheckbox).checked;
    if (idOfCheckbox.toString().includes("authorCheckbox")) {
        if (isChecked) {
            idOfLabel.innerHTML = "New author";
        } else {
            idOfLabel.innerHTML = "Existing author";
        }
    }
    if (idOfCheckbox.toString().includes("sectionCheckbox")) {
        if (isChecked) {
            idOfLabel.innerHTML = "New section";
        } else {
            idOfLabel.innerHTML = "Existing section";
        }
    }
    if (idOfCheckbox.toString().includes("publisherCheckbox")) {
        if (isChecked) {
            idOfLabel.innerHTML = "New publisher";
        } else {
            idOfLabel.innerHTML = "Existing publisher";
        }
    }
    showOrHide(idOfCheckbox);
}

function validateIdValueIfObjectIsNew() {
    let checkboxes = document.querySelectorAll('input[type=checkbox]');

    checkboxes.forEach(checkbox => {
        if (checkbox.checked) {
            document.getElementById('id-' + checkbox.id).value = 0;
        }
    });
    return true;
}
