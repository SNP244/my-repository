# 🎫 QuickDesk - Helpdesk Ticketing System

QuickDesk is a lightweight helpdesk ticketing system designed for small teams. It features user authentication, ticket creation, role-based access (End User & Agent), and a basic comment system with replies.

---

## 🔧 Tech Stack

### 🚀 Backend (Spring Boot)
- Java 17+
- Spring Boot
- Spring Security + JWT
- JPA + Hibernate
- H2 Database

### 🌐 Frontend
- HTML, CSS, JavaScript
- Responsive Design
- No frameworks — Pure JS

---

## 📁 Project Structure

quickdesk/
├── backend/ # Spring Boot project
│ ├── src/
│ └── pom.xml
├── frontend/ # Static frontend
│ ├── index.html
│ ├── style.css
│ └── script.js
└── README.md


---

## 🧩 Features

✅ User Signup & Login  
✅ Role: `END_USER` or `AGENT`  
✅ JWT-based authentication  
✅ Create/view tickets  
✅ Add comments & replies  
✅ Agents can view all tickets  
✅ Frontend dashboard with modals  
✅ Logout functionality

---

## 🧑‍💼 Roles

### `END_USER`
- Register/Login
- Create Tickets
- View Own Tickets
- Add Comments & Replies

### `AGENT`
- Register/Login
- View All Tickets
- Assign Self to Tickets
- Update Ticket Status
- Add Comments & Replies

---

## 🛠️ How to Run the App

### ▶️ Backend

1. Go to the `backend` folder:

   ```bash
 2.  cd backendStart the Spring Boot app:

bash

./mvnw spring-boot:run
# or
mvn spring-boot:run
3. API runs at: http://localhost:8080
 Frontend
Go to the frontend folder:

bash
Copy
Edit
cd frontend
Open index.html with Live Server or manually in your browser.

⚠️ Backend must be running on port 8080.

🔐 JWT Auth Flow
After login, JWT token is stored in JS variable.

All secure API calls include token in Authorization: Bearer <token>.

Role is extracted from token (decoded on frontend) to show/hide agent-only features.

📸 UI Overview
Navbar with Login / Signup / Logout

Dashboard with:

Create Ticket form

My Tickets section

Agent-only: All Tickets section

Comment & Reply system

🤝 Contributing
Feel free to fork the project and raise issues or PRs.

📄 License
This project is for educational/demo purposes.




