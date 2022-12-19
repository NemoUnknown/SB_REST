const form_ed = document.getElementById('formForEditing');
const id_ed = document.getElementById('editId');
const firstName_ed = document.getElementById('editFirstName');
const lastName_ed = document.getElementById('editLastName');
const age_ed = document.getElementById('editAge');
const email_ed = document.getElementById('editEmail');
const password_ed = document.getElementById('editPassword');

async function editModalData(id) {
    const urlDataEd = '/rest/admin/users/' + id;
    let usersPageEd = await fetch(urlDataEd);
    if (usersPageEd.ok) {
        let userData =
            await usersPageEd.json().then(async user => {
                id_ed.value = `${user.id}`;
                firstName_ed.value = `${user.firstName}`;
                lastName_ed.value = `${user.lastName}`;
                age_ed.value = `${user.age}`;
                email_ed.value = `${user.username}`;
                password_ed.value = `${user.password}`;
            })
    } else {
        alert(`Error, ${usersPageEd.status}`)
    }
}

async function editUser() {
    let urlEdit = '/rest/admin/users/' + id_ed.value
    let listOfRole = [];

    for (let i = 0; i < form_ed.rolesForEditing.options.length; i++) {
        if (form_ed.rolesForEditing.options[i].selected) {
            listOfRole.push(form_ed.rolesForEditing.options[i].value);
        }
    }

    let method = {
        method: 'PUT',
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            firstName: form_ed.firstName.value,
            lastName: form_ed.lastName.value,
            age: form_ed.age.value,
            username: form_ed.username.value,
            password: form_ed.password.value,
            roles: listOfRole
        })
    }

    const response = await fetch(urlEdit, method);
    if (response.ok) {
        $('#editCloseBtn').click();
        getMainPage();
    } else {
    let body = await response.json();
    getErrorMessage(body, form_ed);
}
}

function getErrorMessage(message, form) {
    let errorBody = document.getElementById('errorBody')
    errorBody.innerHTML = message.info
    $('#errorModal').modal('toggle')
}
