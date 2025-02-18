import { auth } from "@/lib/auth";
import { Avatar, AvatarFallback } from "@/components/ui/avatar";
import { redirect } from "next/navigation";
import Link from "next/link";
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
import { Button } from "@/components/ui/button";
import { SquarePen } from "lucide-react";

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
          <Link href="/settings">
            <Avatar className="mx-5">
              <AvatarFallback>
                {`${user.firstName[0]}${user.lastName[0]}`}
              </AvatarFallback>
            </Avatar>
          </Link>
        </div>
      </header>
      <main>
        <div className="w-full flex flex-col justify-center items-center">
          <Link href="/shelves">
            <ShelfHeader shelves={shelves} />
          </Link>

          <div className="lg:w-1/2 w-4/5">
            <div className="flex w-full justify-end">
              <Link href="/journal/new">
                <Button variant="outline">
                  <SquarePen />
                </Button>
              </Link>
            </div>
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
