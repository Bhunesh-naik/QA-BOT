# QA-BOT 🧠🎙️
An AI-powered Full Stack Question Answering Bot with Speech Recognition Support

## 📌 Project Overview
QA-BOT is a full-stack intelligent web application designed to provide users with an interactive chatbot experience through both text and voice input.

The project combines a **Spring Boot backend** with a **modern frontend web application** to create a seamless question-answering platform. It supports speech recognition, user authentication, data storage, and API-driven communication between frontend and backend.

This system is useful for building AI assistants, educational bots, speech-enabled applications, and intelligent support systems.

---

# 🚀 Key Features

## 🔐 Authentication System
- User Registration
- User Login
- Secure authentication handling
- Session/user access management

This ensures only authorized users can access the chatbot features.

---

## 🎙️ Speech Recognition
The application supports voice-based interaction.

Users can:
- Upload audio files
- Speak directly through microphone input
- Convert speech into text
- Process spoken questions

Backend uses speech recognition services to analyze and convert audio input.

---

## 🤖 Question Answering Bot
Core chatbot functionality allows users to:
- Ask questions via text
- Ask questions via speech
- Receive intelligent responses
- Interact in a conversational way

This makes the system useful as a smart assistant.

---

## 💾 Database Integration
The backend stores:
- User details
- Speech/audio records
- Processed data
- Application-related information

This helps maintain persistent data across sessions.

---

## 🌐 Frontend User Interface
The frontend provides:
- Clean chatbot interface
- Login page
- Registration page
- Voice input controls
- Interactive response display
- User-friendly navigation

---

## 🔄 REST API Communication
Frontend and backend communicate using REST APIs.

This includes:
- Authentication APIs
- Speech upload APIs
- Chatbot request APIs
- Data retrieval APIs

---

# 🛠️ Technology Stack

## Backend Technologies
- Java
- Spring Boot
- Spring MVC
- Spring Security
- Maven
- JPA / Hibernate
- MySQL
- REST APIs
- Vosk Speech Recognition

---

## Frontend Technologies
- HTML
- CSS
- JavaScript
- React.js
- Axios
- Bootstrap / Custom CSS

---

# 📂 Project Structure

```bash
QA-BOT/
│
├── QA-BOT/                        # Backend Application
│   │
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/naik/QA_BOT/
│   │   │   │       ├── controller/
│   │   │   │       ├── service/
│   │   │   │       ├── entity/
│   │   │   │       ├── repo/
│   │   │   │       └── Application.java
│   │   │   │
│   │   │   └── resources/
│   │   │
│   │   └── test/
│   │
│   ├── pom.xml
│   ├── mvnw
│   └── mvnw.cmd
│
├── healthvoice-insights/          # Frontend Application
│   │
│   ├── public/
│   ├── src/
│   ├── package.json
│   └── package-lock.json
│
└── README.md
