# 🎧 QA-BOT: AI Call Analysis System

A backend system that converts audio calls into text and analyzes them using AI to evaluate customer-agent interactions.

---

## 🚀 Features

* 🔐 **User Authentication (Basic)**

  * Register new users
  * Login with username & password
* 🎤 **Speech-to-Text** using Vosk
* 🧠 **AI Analysis** using Gemini API
* 📊 Sentiment Detection (Positive / Negative / Neutral)
* ⭐ Agent Performance Score (0–100)
* 🎯 Accuracy Score
* ❓ Extract Common Questions
* 🚨 Detect Alerts (sensitive keywords)
* 🗂 Store results in Database
* 📜 Get Call History
* 🔝 Top 5 Common Questions API

---

## 🛠 Tech Stack

* **Backend:** Spring Boot (Java)
* **Authentication:** Basic Login (No JWT)
* **Speech Recognition:** Vosk
* **AI Analysis:** Gemini API
* **Database:** MySQL
* **Build Tool:** Maven

---

## 📂 Project Structure

```id="q1r8w2"
QA-BOT/
│── controller/
│   ├── SpeechController.java
│   └── AuthController.java
│
│── service/
│   ├── VoskService.java
│   ├── GeminiService.java
│   └── AuthService.java
│
│── entity/
│   ├── SpeechRecord.java
│   └── User.java
│
│── repo/
│   ├── SpeechRepository.java
│   └── UserRepository.java
│
│── uploads/
│── application.properties
```

---

## ⚙️ Setup Instructions

### 1️⃣ Clone Repository

```id="c7v2b1"
git clone https://github.com/your-username/QA-BOT.git
cd QA-BOT
```

---

### 2️⃣ Install Dependencies

```id="x3p9k4"
mvn clean install
```

---

### 3️⃣ Setup Database

```id="n5t7m2"
spring.datasource.url=jdbc:mysql://localhost:3306/qabot
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

---

### 4️⃣ Add Gemini API Key

```id="h8k2d9"
gemini.api.key=YOUR_API_KEY
```

---

### 5️⃣ Download Vosk Model

Download:
https://alphacephei.com/vosk/models

Use:

```id="y6l4c1"
vosk-model-en-us-0.22
```

---

### 6️⃣ Install FFmpeg

https://ffmpeg.org/download.html

---

### 7️⃣ Run Project

```id="m3q8x5"
mvn spring-boot:run
```

---

## 🔐 Authentication APIs

### 🟢 Register

```id="p2n7s4"
POST /api/auth/register
```

Body:

```json id="v9d1k3"
{
  "username": "user1",
  "password": "123456"
}
```

---

### 🔵 Login

```id="f6g3r2"
POST /api/auth/login
```

Response:

```json id="k8t5z1"
{
  "message": "Login successful"
}
```

---

## 📡 Main APIs

### 🎤 Upload & Analyze Audio

```id="r1c9x7"
POST /api/speech/transcribe
```

---

### 📜 Get History

```id="u5w3e8"
GET /api/speech/history
```

---

### 🔝 Top 5 Questions

```id="z2m4k6"
GET /api/speech/top-questions
```

---

## 📊 Sample Response

```json id="b7h3q2"
{
  "transcript": "hello sir i want to check my insurance claim status",
  "sentiment": "NEUTRAL",
  "agentScore": 60,
  "accuracy": 80,
  "commonQuestions": ["insurance claim status"],
  "alerts": []
}
```

---

## ⚠️ Notes

* Authentication is basic (no JWT/session management)
* Vosk accuracy depends on audio quality
* For better accuracy, Whisper can be used

---

## 🔥 Future Improvements

* Add JWT authentication
* Build frontend dashboard (React)
* Real-time call analytics
* Admin panel

---

## 👨‍💻 Author

**Bhunesh Naik**

---

## ⭐ Support

If you like this project, give it a ⭐ on GitHub!

---
