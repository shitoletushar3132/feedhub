function toggle(id) {
	var formDiv = document.getElementById(id);
	formDiv.style.display = formDiv.style.display === "none" ? "block"
		: "none";
}


function openModal(title = 'Add Student', id = '', name = '', rollNo = '', email = '', username = '') {
	document.getElementById('formModal').style.display = 'block';
	document.getElementById('formTitle').textContent = title;

	document.querySelector('input[name="name"]').value = name;
	document.querySelector('input[name="rollNo"]').value = rollNo;
	document.querySelector('input[name="email"]').value = email;
	document.querySelector('input[name="username"]').value = username;

	const passwordInput = document.querySelector('input[name="password"]');
	if (title === 'Edit Student') {
		passwordInput.removeAttribute('required');
		passwordInput.placeholder = 'Leave blank to keep unchanged';

		// Change form action
		document.querySelector('form').action = `studentHandler/edit?id=${id}`;
	} else {
		passwordInput.required = true;
		passwordInput.placeholder = 'Enter password';
		document.querySelector('form').action = 'studentHandler/add"';
	}
}


function closeModal() {
	document.getElementById('formModal').style.display = 'none';
	const form = document.querySelector('form');
	form.reset();
	form.action = 'addStudent';
	document.getElementById('formTitle').textContent = 'Add Student';
}



function openTeacherModal(title = 'Add Teacher', id = '', name = '', email = '', username = '') {
	document.getElementById('formTeacherModal').style.display = 'block';
	document.getElementById('formTitle').textContent = title;

	const form = document.getElementById('teacherForm');
	form.name.value = name;
	form.email.value = email;
	form.username.value = username;

	const passwordInput = form.password;
	if (title === 'Edit Teacher') {
		passwordInput.removeAttribute('required');
		passwordInput.placeholder = 'Leave blank to keep unchanged';
		form.action = `teacherHandler/edit?id=${id}`;
	} else {
		passwordInput.required = true;
		passwordInput.placeholder = 'Enter password';
		form.action = 'addTeacher';
	}
}

function closeTeacherModal() {
	const modal = document.getElementById('formTeacherModal');
	modal.style.display = 'none';

	const form = document.getElementById('teacherForm');
	form.reset();
	document.getElementById('formTitle').textContent = 'Add Teacher';
}

// Optional: Close modal when clicking outside
window.onclick = function(event) {
	const modal = document.getElementById('formModal');
	if (event.target == modal) {
		modal.style.display = "none";
	}
};


window.addEventListener("DOMContentLoaded", () => {
	const msg = document.getElementById("error-message");
	if (msg) {
		setTimeout(() => {
			msg.style.display = "none";
		}, 3000); // 3 seconds
	}
});





function deleteStudent(id) {
	fetch('deleteStudent?id=' + id, {
		method: 'GET'
	})
		.then(response => response.text())
		.then(data => {
			alert(data);            // Optional: show response
			location.reload();      // Refresh the page after deletion
		})
		.catch(error => {
			console.error('Error:', error);
			alert('Something went wrong.');
		});
}



function deleteTeacher(id) {
	fetch('deleteTeacher?id=' + id, {
		method: 'GET'
	})
		.then(response => response.text())
		.then(data => {
			alert(data);            // Optional: show response
			location.reload();      // Refresh the page after deletion
		})
		.catch(error => {
			console.error('Error:', error);
			alert('Something went wrong.');
		});
}


function deleteSubject(id) {
	fetch('deleteSubject?id=' + id, {
		method: 'GET'
	})
		.then(response => response.text())
		.then(data => {
			alert(data);            // Optional: show response
			location.reload();      // Refresh the page after deletion
		})
		.catch(error => {
			console.error('Error:', error);
			alert('Something went wrong.');
		});
}
