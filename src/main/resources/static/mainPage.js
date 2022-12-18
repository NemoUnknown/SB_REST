const url = '/rest/admin/mainPage'

async function getMainPage() {
    let page = await fetch(url)

    if (page.ok) {
        let listUsers = await page.json();
        loadTableData(listUsers)
    } else {
        alert(`Error, ${page.status}`);
    }
}

function loadTableData(listUsers) {
    const tableBody = document.getElementById('tbody');
    let dataHtml = '';

    for (let person of listUsers) {
        let roles = [];

        for (let role of person.roles) {
            roles.push(" " + role.name.toString().replaceAll('ROLE_', ''))
        }
        dataHtml += `<tr>
            <td>${person.id}</td>
            <td>${person.firstName}</td>
            <td>${person.lastName}</td>
            <td>${person.age}</td>
            <td>${person.username}</td>
            <td>${roles}</td>
            <td>
                <button class="btn btn-primary" data-bs-toggle="modal" 
                           data-bs-target="#editModal"
                           onclick="editModalData(${person.id})">
                    Edit
                </button>
            </td>
            <td>
                <button class="btn btn-danger" data-bs-toggle="modal" 
                           data-bs-target="#deleteModal"
                           onclick="deleteModalData(${person.id})">
                    Delete
                </button>
            </td>
            </tr>`
    }
    tableBody.innerHTML = dataHtml;
}

getMainPage();