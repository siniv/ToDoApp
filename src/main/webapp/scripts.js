searchData();

function searchData() {
    axios.get('http://localhost:8080/api/todot')
        .then(function (response) {
            printToTable(response.data);

        })
        .catch(function (error) {
            console.log(error);
        })
}

function printToTable(todos) {
    const table = document.getElementById("toDoTable");
    for (const t of todos) {
        table.innerHTML += "<tr><td>" + t.name + "</td><td>asap</td><td><button onclick='remove(" + t.id + ")'>Done</button></td></tr>"
    }
}

function remove(id) {
    console.log(id)
    axios.delete(`http://localhost:8080/api/todot/${id}`)
        .then(function (response) {
            location.reload();
        })
        .catch
        (function (error) {
            console.log(error);
        })

    console.log(removed)
}

function addToTable(todo) {
    const table = document.getElementById("toDoTable");
    table.innerHTML += "<tr><td>" + todo.name + "</td><td>asap</td><td><button onclick='remove(" + todo.id + ")'>Done</button></td></tr>"

}

function addData() {
    var taskTitle = document.getElementById('taskTitle').value;
    axios.post('http://localhost:8080/api/todot', {
        name: `${taskTitle}`
    })
        .then(function (response) {
            addToTable(response.data);
        })
        .catch(function (error) {
            console.log(error);
        })
}





