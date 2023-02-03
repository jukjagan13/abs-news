# csw-schedule

1. Create empty database
2. Update environment variables
3. Run Project
4. Use swagger to test APIs (Swagger URL: **http://localhost:8080/csw/swagger-ui.html**)
5. Across all API use **(dd MMM yyyy)** date format

```
Environment Variables:
DB_URL=jdbc:mysql://localhost:3306/database_name?&autoReconnect=True;
DB_USERNAME=username;
DB_PASSWORD=password
```

API Base URL: **http://localhost:8080/csw**

```
1. New Schedule
Method: PUT
URL: {baseUrl}/{employeeId}/schedule
Sample Payload:
{
  "startDate": "01 May 2021",
  "endDate": "01 Aug 2021",
  "time": "12:00",
  "duration": "30",
  "repeat": true,
  "frequency": "Daily"
}
```

```
2. Get Schedule
Method: GET
URL: {baseUrl}/{employeeId}/schedule
Param: withHistory (optional)
```

```
3. Modify Schedule
Method: POST
URL: {baseUrl}/{employeeId}/schedule/modify
Sample Payload:
{
  "startDate": "01 May 2021",
  "endDate": "01 Aug 2021",
  "time": "11:00",
  "duration": "30",
  "repeat": true,
  "frequency": "Weekdays"
}
```

```
4. Cancel Schedule
Method: POST
URL: {baseUrl}/{employeeId}/schedule/cancel
```

```
5. Get Schedule By Date
Method: GET
URL: {baseUrl}/schedules/by-date/{date}
```

```
//Additional API to complete schedule
6. Complete Schedule
Method: PUT
URL: {baseUrl}/{employeeId}/schedule/complete
```
