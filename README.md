# Golify Excel Data Importer
### _For uploading excel file and importing data using batch processing_

This a sample Spring Boot based RESTful API to enable uploading of excel files, import data from the file using batch processing, keeping track of the status and list of imported data. This API secured using Spring Security with role management based on JWT tokens.

## Features
- Upload excel files
- Automatic batch procesing of uplaoded excel file
- Import data from excel file to database via batch processes
- Check batch processing status in realtime
- Fetch list of uploaded files and data
- Delete uploaded file with records cleanup
- Role management based access to features using JWT

## Tech
The following tech stacks has been implemented in development of this API:
- [Spring Boot](https://spring.io/) - Create RESTful web service
- [JWT](https://jwt.io/) - Role base access management
- [Swagger](https://swagger.io/) - API documentation

## API Endpoints
1. Get JWT for access
```sh
curl --location --request POST 'localhost:8080/golify/api/login' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'username=wasif' \
--data-urlencode 'password=12345'
```

2. Upload excel file for importing data
```sh
curl --location --request POST 'localhost:8080/golify/api/file/upload' \
--header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3YXNpZiIsInJvbGVzIjpbIkFETUlOIiwiVVNFUiJdLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvZ29saWZ5L2FwaS9sb2dpbiIsImV4cCI6MTY2OTM5MzA1NX0.LBOb_cGzoFpNfxWhCSjOBtGps0nP-FnhknSjALrqSdM' \
--form 'file=@"/C:/Users/Wasif/Desktop/Workspace/Employee.xls"'
```

3. Check data importing status
```sh
curl --location --request GET 'localhost:8080/golify/api/file/upload/status/1' \
--header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3YXNpZiIsInJvbGVzIjpbIkFETUlOIiwiVVNFUiJdLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvZ29saWZ5L2FwaS9sb2dpbiIsImV4cCI6MTY2OTM5MzA1NX0.LBOb_cGzoFpNfxWhCSjOBtGps0nP-FnhknSjALrqSdM'
```

4. List of uploaded file
```sh
curl --location --request GET 'localhost:8080/golify/api/file/list' \
--header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3YXNpZiIsInJvbGVzIjpbIkFETUlOIiwiVVNFUiJdLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvZ29saWZ5L2FwaS9sb2dpbiIsImV4cCI6MTY2OTM5MzA1NX0.LBOb_cGzoFpNfxWhCSjOBtGps0nP-FnhknSjALrqSdM'
```

5. List of imported data for specific file
```sh
curl --location --request GET 'localhost:8080/golify/api/file/records/1' \
--header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3YXNpZiIsInJvbGVzIjpbIkFETUlOIiwiVVNFUiJdLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvZ29saWZ5L2FwaS9sb2dpbiIsImV4cCI6MTY2OTM5MzA1NX0.LBOb_cGzoFpNfxWhCSjOBtGps0nP-FnhknSjALrqSdM'
```

6. Delete uploaded file with records cleanup
```sh
curl --location --request DELETE 'localhost:8080/golify/api/file/delete/1' \
--header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3YXNpZiIsInJvbGVzIjpbIkFETUlOIiwiVVNFUiJdLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvZ29saWZ5L2FwaS9sb2dpbiIsImV4cCI6MTY2OTM5MzA1NX0.LBOb_cGzoFpNfxWhCSjOBtGps0nP-FnhknSjALrqSdM'
```

## License
MIT
