from locust import HttpUser, task, between

class SpringUser(HttpUser):
    wait_time = between(1, 3)

    host = "http://localhost:8080"

    @task
    def detect_objects(self):
        self.client.get("/analyze_img?url=https://img.freepik.com/free-photo/people-posing-together-registration-day_23-2149096793.jpg?semt=ais_hybrid&w=740&q=80")