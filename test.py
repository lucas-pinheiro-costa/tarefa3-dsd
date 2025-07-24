import requests

url = "http://localhost:8000/api/users/by-email/"
params = {"email": "teste@email.com"}

response = requests.get(url, params=params)
response.raise_for_status()
print(response.json())