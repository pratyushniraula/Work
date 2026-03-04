# **App Name**: HighSchoolHub

## Core Features:

- Student Listing: List student details (Student_id, Name, Address, Phone_no, Date_of_birth, GPA, Year) in a paginated table format with search/filter options.
- Student Detail View: Display and allow editing of all student attributes, plus Grades and Attendance links.
- Teacher Listing: List teacher details (Teacher_id, Name, Subject, Department, Phone_no, Email, Office_location, Office_hours, Teacher_assistant_id) in a paginated table format with search/filter.
- Teacher Detail View: Display and allow editing of all teacher attributes, plus a "Classes taught" overview.

## Style Guidelines:

- Primary color: Neutral grays for a professional look.
- Secondary color: A calming blue (#42A5F5) to provide visual interest without being distracting.
- Accent: Teal (#009688) for interactive elements like buttons and links.
- Responsive grid layout that adapts to different screen sizes.
- Sidebar navigation with clear and concise labels.
- Consistent use of Material Design icons for a polished look.

## Original User Request:
Build me a professional admin dashboard web app (React + your choice of UI library—Material UI, Tailwind, etc.) for a high school directory.  
It needs two main sections:  

1. **Students**  
   - List view: table with columns → Student_id, Name, Address, Phone_no, Date_of_birth, GPA, Year  
   - Detail/Edit view: all student attributes + Grades and Attendance links  
   - Search/filter by name, year, GPA range  

2. **Teachers**  
   - List view: table with columns → Teacher_id, Name, Subject, Department, Phone_no, Email, Office_location, Office_hours, Teacher_assistant_id  
   - Detail/Edit view: all teacher attributes, plus “Classes taught” overview  
   - Search/filter by name, department  

**Common requirements:**  
- Responsive layout with a sidebar for navigation.  
- Global header with app title and user menu (e.g. “Admin”).  
- Routing setup (e.g. React Router) for `/students`, `/students/:id`, `/teachers`, `/teachers/:id`.  
- Placeholder API layer in `/src/api/` with files `students.js` and `teachers.js`.  
  - In each file, include comments like:  
    ```js
    // TODO: initialize and export Firestore instance
    // TODO: write getStudents() to fetch from 'students' collection
    // TODO: write createStudent(data), updateStudent(id, data), deleteStudent(id)
    ```  
- In components, add comments marking where to hook into those API functions, e.g.:  
  ```jsx
  // TODO: call getStudents() here and set state
  useEffect(() => { /* ... */ }, []);
  