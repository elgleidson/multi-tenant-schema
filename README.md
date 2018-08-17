# multi-tenant-schema

A simple multi-tenant application using spring boot.
This application uses a "schema per tenant" approach and select the tenant not by HTTP header ("X-Tenant-ID" for example) but instead uses the current logged user to do this.
Each user of the application has a tenant associated with him.
Each tenant connection properties is stored in the database along with the users.

This application has 3 schemas:
* **core**, used by application to stores the _users_, _roles_ and _tenant_ properties.
* **user1** used by _user1_.
* **user2**, used by _user2_.
