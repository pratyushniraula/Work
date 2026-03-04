from graphviz import Digraph

# Create ER Diagram
er = Digraph('ERDiagram', filename='ponder_high_school_db', format='png')

# Define Entities
entities = {
    "Student": ["studentID (PK)", "name", "date_of_birth", "address", "phone_number", "year", "GPA"],
    "Teacher": ["teacherID (PK)", "name", "phone_number", "email", "subject", "department", "office_location", "office_hours", "teacher_assistantID (FK)"],
    "Class": ["classID (PK)", "course_code", "class_name", "department", "honors_AP", "semester", "year", "location", "schedule", "teacherID (FK)"],
    "Enrollment": ["enrollmentID (PK)", "studentID (FK)", "classID (FK)"],
    "Grades": ["studentID (PK, FK)", "courseID (PK, FK)", "grade"],
    "Attendance": ["studentID (PK, FK)", "date (PK)", "status"]
}

# Add entities to the diagram
for entity, attributes in entities.items():
    er.node(entity, label=f"{entity}\n" + "\n".join(attributes), shape="box")

# Define Relationships
relationships = [
    ("Student", "Enrollment", "1:N"),
    ("Class", "Enrollment", "1:N"),
    ("Teacher", "Class", "1:N"),
    ("Class", "Grades", "1:N"),
    ("Student", "Grades", "1:N"),
    ("Student", "Attendance", "1:N")
]

# Add relationships to the diagram
for source, target, relation in relationships:
    er.edge(source, target, label=relation)

# Render and display the ER Diagram
er_path = "."
er.render(er_path, format="png", cleanup=True)
er_path

