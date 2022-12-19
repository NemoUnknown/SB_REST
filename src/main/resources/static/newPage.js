const url_new = '/rest/admin/new';
const roles_new = document.querySelector('#roles').selectedOptions;
const form_new = document.forms["formForCreatingNewUser"];

async function newUser() {
    form_new.addEventListener('submit', addNewUser)
}

async function addNewUser(e) {
    e.preventDefault();
    let listOfRole = [];
    for (let i = 0; i < roles_new.length; i++) {
        listOfRole.push(roles_new[i].value);
    }

    let method = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            firstName: form_new.firstName.value,
            lastName: form_new.lastName.value,
            age: form_new.age.value,
            username: form_new.username.value,
            password: form_new.password.value,
            roles: listOfRole
        })
    }

    const response = await fetch(url_new, method);
    if (response.ok) {
        form_new.reset();
        getMainPage();
        $("#tabBtnAllUsers").click();
    } else {
        let body = await response.json();
        getErrorMessage(body, form_new);
    }

}

function getErrorMessage(message, form) {
    let errorBody = document.getElementById('errorBody')

    errorBody.innerHTML = message.info
    form.password.value = ''
    $('#errorModal').modal('toggle')
}