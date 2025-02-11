import { auth } from "@/lib/auth";
import { Avatar, AvatarFallback } from "@/components/ui/avatar";
import { redirect } from "next/navigation";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";

export default async function Home() {
  const session = await auth();

  const user = session?.user;
  if (!user) {
    redirect("/auth/login");
  }

  // Test Data
  const journals = [
    {
      title: "Journal #1",
      description: "Journal #1 Description",
    },
    {
      title: "Journal #2",
      description: "Journal #2 Description",
    },
    {
      title: "Journal #3",
      description: "Journal #3 Description",
    },
  ];

  return (
    <>
      <header className="flex justify-between items-center w-screen my-5">
        <div>
          <Avatar className="mx-5">
            <AvatarFallback>
              {`${user.firstName[0]}${user.lastName[0]}`}
            </AvatarFallback>
          </Avatar>
          <span></span>
        </div>
      </header>
      <main>
        <div className="w-full flex flex-col justify-center items-center">
          <h1 className="text-3xl my-10">Shelf Name</h1>
          <div className="lg:w-1/2 w-4/5">
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead>Title</TableHead>
                  <TableHead>Description</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {journals.map((journal, index) => {
                  return (
                    <TableRow key={index}>
                      <TableCell>{journal.title}</TableCell>
                      <TableCell>{journal.description}</TableCell>
                    </TableRow>
                  );
                })}
              </TableBody>
            </Table>
          </div>
        </div>
      </main>
    </>
  );
}
