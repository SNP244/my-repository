let token = '';
function getUserRoleFromToken(token) {
  try {
    const payload = JSON.parse(atob(token.split('.')[1]));
    return payload.role || payload["role"];
  } catch (e) {
    return null;
  }
}



function showModal(id) {
  document.getElementById(id).classList.remove('hidden');
}

function hideModal(id) {
  document.getElementById(id).classList.add('hidden');
}

function showDashboard(role) {
  document.getElementById('dashboard').classList.remove('hidden');
  fetchMyTickets();

  const allTicketsSection = document.getElementById('allTicketsSection');
  if (role === 'AGENT') {
    allTicketsSection.classList.remove('hidden');
  } else {
    allTicketsSection.classList.add('hidden');
  }

  document.getElementById('authButtons').classList.add('hidden');
  document.getElementById('logoutSection').classList.remove('hidden');
}

function login() {
  const email = document.getElementById('loginEmail').value;
  const password = document.getElementById('loginPassword').value;

  fetch('http://localhost:8080/api/auth/login', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ email, password })
  })
    .then(res => res.json())
    .then(data => {
      token = data.token;
      const role = getUserRoleFromToken(token);
      hideModal('loginModal');
      showDashboard(role);
    })
    .catch(() => alert(' Login failed.'));
}





//  Signup
function signup() {
  const name = document.getElementById('signupName').value;
  const email = document.getElementById('signupEmail').value;
  const password = document.getElementById('signupPassword').value;
  const role = document.getElementById('signupRole').value;

  fetch('http://localhost:8080/api/auth/register', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ name, email, password, role })
  })
    .then(res => {
      if (res.ok) {
        alert(' Signup successful!');
        hideModal('signupModal');
        showModal('loginModal');
      } else {
        alert(' Signup failed.');
      }
    });
}

function logout() {
  token = '';
  document.getElementById('dashboard').classList.add('hidden');
  document.getElementById('logoutSection').classList.add('hidden');
  document.getElementById('authButtons').classList.remove('hidden');
  showModal('loginModal');
}


//  Create Ticket
function createTicket() {
  const subject = document.getElementById('subject').value;
  const description = document.getElementById('description').value;
  const categoryId = document.getElementById('categoryId').value;

  fetch('http://localhost:8080/api/tickets', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + token
    },
    body: JSON.stringify({
      subject,
      description,
      category: { id: parseInt(categoryId) }
    })
  })
    .then(res => res.json())
    .then(data => {
      alert(' Ticket created with ID: ' + data.id);
      fetchMyTickets();
    });
}

//  View Tickets
function fetchMyTickets() {
  fetch('http://localhost:8080/api/tickets/my', {
    headers: { 'Authorization': 'Bearer ' + token }
  })
    .then(res => res.json())
    .then(data => {
      const list = document.getElementById('ticketList');
      list.innerHTML = '';
      data.forEach(ticket => {
        const li = document.createElement('li');
        li.innerText = `#${ticket.id}: ${ticket.subject} - ${ticket.status}`;
        list.appendChild(li);
      });
    });
}

function fetchAllTickets() {
  fetch('http://localhost:8080/api/tickets', {
    headers: { 'Authorization': 'Bearer ' + token }
  })
    .then(res => res.json())
    .then(data => {
      const list = document.getElementById('allTicketList');
      list.innerHTML = '';
      data.forEach(ticket => {
        const li = document.createElement('li');
        li.innerText = `#${ticket.id}: ${ticket.subject} - ${ticket.status}`;
        list.appendChild(li);
      });
    });
}


//  Add Comment
function addComment() {
  const ticketId = document.getElementById('ticketId').value;
  const message = document.getElementById('commentMessage').value;

  fetch(`http://localhost:8080/api/comments/${ticketId}`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + token
    },
    body: JSON.stringify({ message })
  }).then(() => loadComments(ticketId));
}

//  Load Comments with Replies
function loadComments(ticketId) {
  fetch(`http://localhost:8080/api/comments/${ticketId}`, {
    headers: { 'Authorization': 'Bearer ' + token }
  })
    .then(res => res.json())
    .then(comments => {
      const list = document.getElementById('commentList');
      list.innerHTML = '';
      comments.forEach(c => {
        const li = document.createElement('li');
        li.innerHTML = `<b>${c.author.name}</b>: ${c.message}`;

        if (c.replies?.length > 0) {
          const replyUl = document.createElement('ul');
          c.replies.forEach(r => {
            const rli = document.createElement('li');
            rli.innerText = `↪ ${r.author.name}: ${r.message}`;
            replyUl.appendChild(rli);
          });
          li.appendChild(replyUl);
        }

        list.appendChild(li);
      });
    });
}
