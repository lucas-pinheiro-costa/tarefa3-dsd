from django.urls import path
from .views import (
    UserRegistrationView,
    SensorRegistrationView,
    ListUserSensorsView,
    GetLatestSensorDataView,
    GetUserByEmailView,
    GenerateSensorDataView
)

urlpatterns = [
    path('users/register/', UserRegistrationView.as_view(), name='register-user'),
    path('users/by-email/', GetUserByEmailView.as_view(), name='get-user-by-email'),
    path('users/<int:user_id>/sensors/', ListUserSensorsView.as_view(), name='list-user-sensors'),
    path('sensors/register/', SensorRegistrationView.as_view(), name='register-sensor'),
    path('sensors/<str:sensor_id>/latest-data/', GetLatestSensorDataView.as_view(), name='get-latest-sensor-data'),
    path('sensors/<str:sensor_id>/generate-data/', GenerateSensorDataView.as_view(), name='generate-sensor-data'),
]