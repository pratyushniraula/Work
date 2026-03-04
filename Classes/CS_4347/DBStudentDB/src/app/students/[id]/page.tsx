
"use client";

import React, { useState, useEffect } from 'react';
import { useParams, useRouter } from 'next/navigation';
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Button } from "@/components/ui/button";
import { Calendar } from "@/components/ui/calendar"
import { CalendarIcon } from "lucide-react"
import { Popover, PopoverContent, PopoverTrigger } from "@/components/ui/popover"
import { cn } from "@/lib/utils"
import { format } from "date-fns"

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

const StudentDetailPage: React.FC = () => {
  const { id } = useParams();
  const router = useRouter();
  const [student, setStudent] = useState<Student | null>(null);
  const [name, setName] = useState('');
  const [address, setAddress] = useState('');
  const [phoneNo, setPhoneNo] = useState('');
  const [dateOfBirth, setDateOfBirth] = useState<Date | undefined>(undefined);
  const [gpa, setGPA] = useState('');
  const [year, setYear] = useState('');

  // TODO: call getStudent(id) here and set state
  useEffect(() => {
    // Placeholder data for now
    const dummyStudent: Student = {
      Student_id: id as string,
      Name: 'Alice Smith',
      Address: '123 Main St',
      Phone_no: '555-1212',
      Date_of_birth: '2005-01-01',
      GPA: 3.8,
      Year: 12,
    };
    setStudent(dummyStudent);
    setName(dummyStudent.Name);
    setAddress(dummyStudent.Address);
    setPhoneNo(dummyStudent.Phone_no);
    setDateOfBirth(new Date(dummyStudent.Date_of_birth));
    setGPA(dummyStudent.GPA.toString());
    setYear(dummyStudent.Year.toString());
  }, [id]);

  const handleSave = () => {
    if (!student) return;

    // TODO: call updateStudent(id, data) here
    const updatedStudent = {
      ...student,
      Name: name,
      Address: address,
      Phone_no: phoneNo,
      Date_of_birth: dateOfBirth?.toISOString().slice(0, 10) || '',
      GPA: parseFloat(gpa),
      Year: parseInt(year),
    };
    console.log('Saving student:', updatedStudent);
    // After successful save, navigate back to the students list
    router.push('/students');
  };

  if (!student) {
    return <div>Loading...</div>;
  }

  return (
    <div>
      <h1 className="text-2xl font-bold mb-4">Student Detail</h1>

      <div className="grid gap-4">
        <div>
          <Label htmlFor="name">Name</Label>
          <Input
            type="text"
            id="name"
            value={name}
            onChange={e => setName(e.target.value)}
          />
        </div>
        <div>
          <Label htmlFor="address">Address</Label>
          <Input
            type="text"
            id="address"
            value={address}
            onChange={e => setAddress(e.target.value)}
          />
        </div>
        <div>
          <Label htmlFor="phone">Phone Number</Label>
          <Input
            type="text"
            id="phone"
            value={phoneNo}
            onChange={e => setPhoneNo(e.target.value)}
          />
        </div>
        <div>
        <Label>Date of Birth</Label>
          <Popover>
            <PopoverTrigger asChild>
              <Button
                variant={"outline"}
                className={cn(
                  "w-[240px] justify-start text-left font-normal",
                  !dateOfBirth && "text-muted-foreground"
                )}
              >
                <CalendarIcon className="mr-2 h-4 w-4" />
                {dateOfBirth ? format(dateOfBirth, "PPP") : <span>Pick a date</span>}
              </Button>
            </PopoverTrigger>
            <PopoverContent className="w-auto p-0" align="center" side="bottom">
              <Calendar
                mode="single"
                selected={dateOfBirth}
                onSelect={setDateOfBirth}
                disabled={(date) =>
                  date > new Date() || date < new Date("1900-01-01")
                }
                initialFocus
              />
            </PopoverContent>
          </Popover>
        </div>
        <div>
          <Label htmlFor="gpa">GPA</Label>
          <Input
            type="number"
            id="gpa"
            value={gpa}
            onChange={e => setGPA(e.target.value)}
          />
        </div>
        <div>
          <Label htmlFor="year">Year</Label>
          <Input
            type="number"
            id="year"
            value={year}
            onChange={e => setYear(e.target.value)}
          />
        </div>
      </div>

      <div className="mt-4">
        <Button variant="secondary" onClick={handleSave}>Save</Button>
      </div>

      <div className="mt-8">
        <h2 className="text-xl font-bold mb-2">Grades and Attendance</h2>
        <p>
          <a href="#" className="text-blue-500 hover:underline">View Grades</a>
        </p>
        <p>
          <a href="#" className="text-blue-500 hover:underline">View Attendance</a>
        </p>
      </div>
    </div>
  );
};

export default StudentDetailPage;
