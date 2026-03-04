
// TODO: initialize and export Firestore instance

/**
 * Fetches all students from the 'students' collection.
 * @returns {Promise<Student[]>} A promise that resolves to an array of Student objects.
 */
export async function getStudents() {
  // TODO: write getStudents() to fetch from 'students' collection
  return [];
}

/**
 * Creates a new student in the 'students' collection.
 * @param {object} data The student data to be added.
 * @returns {Promise<string>} A promise that resolves to the ID of the newly created student.
 */
export async function createStudent(data: any) {
  // TODO: write createStudent(data)
  return "";
}

/**
 * Updates an existing student in the 'students' collection.
 * @param {string} id The ID of the student to update.
 * @param {object} data The updated student data.
 * @returns {Promise<void>}
 */
export async function updateStudent(id: string, data: any) {
  // TODO: write updateStudent(id, data)
}

/**
 * Deletes a student from the 'students' collection.
 * @param {string} id The ID of the student to delete.
 * @returns {Promise<void>}
 */
export async function deleteStudent(id: string) {
  // TODO: write deleteStudent(id)
}
