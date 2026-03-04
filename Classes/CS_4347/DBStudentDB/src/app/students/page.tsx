"use client";

import React, { useState, useEffect } from 'react';
import {
  Table,
  TableHeader,
  TableBody,
  TableRow,
  TableHead,
  TableCell,
} from "@/components/ui/table";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { PlusCircle, Edit, Trash2 } from "lucide-react";
import Link from 'next/link';
import {
  DropdownMenu,
  DropdownMenuTrigger,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
} from "@/components/ui/dropdown-menu"

// Define the Student type
interface Student {
  Student_id: string;
  Name: string;
  Address: string;
  Phone_no: string;
  Date_of_birth: string;
  GPA: number;
  Year: number;
}

const StudentsPage: React.FC = () => {
  const [students, setStudents] = useState<Student[]>([]);
  const [searchQuery, setSearchQuery] = useState('');

  // TODO: Initialize Firestore and get the students collection

  // TODO: Fetch students from Firestore on mount
  useEffect(() => {
    // Placeholder data for now
    const dummyStudents: Student[] = [
      { Student_id: '1', Name: 'Alice Smith', Address: '123 Main St', Phone_no: '555-1212', Date_of_birth: '2005-01-01', GPA: 3.8, Year: 12 },
      { Student_id: '2', Name: 'Bob Johnson', Address: '456 Elm St', Phone_no: '555-3434', Date_of_birth: '2006-02-02', GPA: 3.5, Year: 11 },
      { Student_id: '3', Name: 'Charlie Brown', Address: '789 Oak St', Phone_no: '555-5656', Date_of_birth: '2007-03-03', GPA: 3.2, Year: 10 },
    ];
    setStudents(dummyStudents);
  }, []);

  const filteredStudents = students.filter(student => {
    const searchMatch = student.Name.toLowerCase().includes(searchQuery.toLowerCase());
    return searchMatch;
  });

  return (
    <div>
      <div className="flex justify-between items-center mb-4">
        <h1 className="text-2xl font-bold">Students</h1>
        <Button asChild>
          <Link href="/students/new">
            <PlusCircle className="mr-2 h-4 w-4" /> Add Student
          </Link>
        </Button>
      </div>

      <div className="flex space-x-4 mb-4">
        <Input
          type="text"
          placeholder="Search by name"
          value={searchQuery}
          onChange={e => setSearchQuery(e.target.value)}
        />
      </div>

      <Table>
        <TableHeader>
          <TableRow>
            <TableHead>Student ID</TableHead>
            <TableHead>Name</TableHead>
            <TableHead>Address</TableHead>
            <TableHead>Phone No</TableHead>
            <TableHead>Date of Birth</TableHead>
            <TableHead>GPA</TableHead>
            <TableHead>Year</TableHead>
            <TableHead className="text-right">Actions</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {filteredStudents.map(student => (
            <TableRow key={student.Student_id}>
              <TableCell>{student.Student_id}</TableCell>
              <TableCell>{student.Name}</TableCell>
              <TableCell>{student.Address}</TableCell>
              <TableCell>{student.Phone_no}</TableCell>
              <TableCell>{student.Date_of_birth}</TableCell>
              <TableCell>{student.GPA}</TableCell>
              <TableCell>{student.Year}</TableCell>
              <TableCell className="text-right">
                <DropdownMenu>
                  <DropdownMenuTrigger asChild>
                    <Button variant="ghost" className="h-8 w-8 p-0">
                      <span className="sr-only">Open menu</span>
                      <Edit className="h-4 w-4"/>
                    </Button>
                  </DropdownMenuTrigger>
                  <DropdownMenuContent align="end">
                    <DropdownMenuLabel>Actions</DropdownMenuLabel>
                    <DropdownMenuItem asChild>
                      <Link href={`/students/${student.Student_id}`}>
                        View/Edit
                      </Link>
                    </DropdownMenuItem>
                    <DropdownMenuSeparator />
                    <DropdownMenuItem>
                      <Button variant="destructive" size="sm">
                        Delete
                      </Button>
                    </DropdownMenuItem>
                  </DropdownMenuContent>
                </DropdownMenu>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </div>
  );
};

export default StudentsPage;
