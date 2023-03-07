# UserController
## POST /users
Create a new user.

### Request Body

```json
{
    "firstName": "string",
    "lastName": "string",
    "email": "string",
    "password": "string"
}
```
- firstName: (string, required) The user's first name.
- lastName: (string, required) The user's last name.
- email: (string, required) The user's email address.
- password: (string, required) The user's password.

### Response Body

```json
{
    "id": "long",
    "firstName": "string",
    "lastName": "string",
    "email": "string",
    "password": "string"
}
```
- id: (long) The ID of the newly created user.
- firstName: (string) The user's first name.
- lastName: (string) The user's last name.
- email: (string) The user's email address.
- password: (string) The user's password.

## GET /users/{id}
Get a user by ID.

### Path Parameters

- id: (long, required) The ID of the user.

### Response Body

```json
{
    "id": "long",
    "firstName": "string",
    "lastName": "string",
    "email": "string",
    "password": "string"
}
```

- id: (long) The ID of the user.
- firstName: (string) The user's first name.
- lastName: (string) The user's last name.
- email: (string) The user's email address.
- password: (string) The user's password.

## GET /users/email
Get a user by email.

### Request Body

```json
{
    "email": "string"
}
```
- email: (string, required) The email address of the user.

### Response Body
```json
{
    "id": "long",
    "firstName": "string",
    "lastName": "string",
    "email": "string",
    "password": "string"
}

```
- id: (long) The ID of the user.
- firstName: (string) The user's first name.
- lastName: (string) The user's last name.
- email: (string) The user's email address.
- password: (string) The user's password.

## PUT /users/{id}
Update a user by ID.

### Path Parameters

- id: (long, required) The ID of the user to update.

### Request Body

```json
{
    "firstName": "string",
    "lastName": "string",
    "email": "string",
    "password": "string"
}
```
- firstName: (string, optional) The updated first name of the user.
- lastName: (string, optional) The updated last name of the user.
- email: (string, optional) The updated email address of the user.
- password: (string, optional) The updated password of the user.

### Response Body

```json
{
    "id": "long",
    "firstName": "string",
    "lastName": "string",
    "email": "string",
    "password": "string"
}
```
- id: (long) The ID of the updated user.
- firstName: (string) The updated first name of the user.
- lastName: (string) The updated last name of the user.
- email: (string) The updated email address of the user.
- password: (string) The updated password of the user.

## DELETE /users/{id}
Deletes a user by ID.

### Path Parameters

- id: (long, required) The ID of the user to update.

### Response

- 204 No Content: If the user was successfully deleted.
- 404 Not Found: If a user with the specified ID does not exist.