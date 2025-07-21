from django.urls import path
from .views import (
    UserRegistrationView,
    SensorRegistrationView,
    ListUserSensorsView,
    GetLatestSensorDataView,
    GetUserByEmailView
)

urlpatterns = [
    path('users/register/', UserRegistrationView.as_view(), name='register-user'),
    path('users/by-email/', GetUserByEmailView.as_view(), name='get-user-by-email'),
    path('users/<int:user_id>/sensors/', ListUserSensorsView.as_view(), name='list-user-sensors'),
    path('sensors/register/', SensorRegistrationView.as_view(), name='register-sensor'),
    path('sensors/<str:sensor_id>/data/', GetLatestSensorDataView.as_view(), name='get-latest-sensor-data'),
]

