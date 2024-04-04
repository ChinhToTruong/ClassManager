# CLASS MANAGEMENT


> <p>This project make your class management be easier</p>

### API description
#### 1.Summary: ---- api/summary
> <p>- user/id: return the report which statistics time login of user and total time login </p>

#### 2.Auth: ---- api/auth

> <p>- login: return token and add your login time for summary</p>
> <p>- register: add new account without information</p>
> <p>- update: update account information</p>


#### 3. Role: ---- api/role

> <p>- all-roles: get all roles</p>
> <p>- create-new-role: create new roles (allow to create many roles once</p>

#### 4. Room: ---- api/room
> <p>- get-all: get all rooms</p>
> <p>- id: get all student by id room</p>
> <p>- create: create new room</p>
> <p>- update/id: update information room includes: name, description, student and teacher</p>


#### 5.Student: ---- api/student
> <p>- room/id: get students by room id</p>

#### 6.Teacher: ---- api/teacher
> <p>- get-all: get all teachers</p>
> <p>- delete: delete teacher</p>


