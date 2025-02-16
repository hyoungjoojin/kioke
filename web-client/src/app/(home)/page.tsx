import { auth } from "@/lib/auth";
import { Avatar, AvatarFallback } from "@/components/ui/avatar";
import { redirect } from "next/navigation";
import {
  Table,
  TableBody,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import ShelfHeader from "@/components/pages/home/ShelfHeader";
import { getShelves } from "../api/shelf";
import JournalList from "@/components/pages/home/JournalList";

export default async function Home() {
  const session = await auth();

  const user = session?.user;
  if (!user) {
    redirect("/auth/login");
  }

  const shelves = await getShelves();

  return (
    <>
      <header className="flex justify-between items-center w-screen my-5">
        <div>
          <Avatar className="mx-5">
            <AvatarFallback>
              {`${user.firstName[0]}${user.lastName[0]}`}
            </AvatarFallback>
          </Avatar>
        </div>
      </header>
      <main>
        <div className="w-full flex flex-col justify-center items-center">
          <ShelfHeader shelves={shelves} />

          <div className="lg:w-1/2 w-4/5">
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead>Title</TableHead>
                  <TableHead>Description</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                <JournalList />
              </TableBody>
            </Table>
          </div>
        </div>
      </main>
    </>
  );
}
