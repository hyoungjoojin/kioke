import { auth } from "@/lib/auth";
import { redirect } from "next/navigation";
import Link from "next/link";
import ShelfHeader from "@/components/pages/home/ShelfHeader";
import { getShelves } from "../api/shelf";
import JournalList from "@/components/pages/home/JournalList";
import { Button } from "@/components/ui/button";
import { SquarePen } from "lucide-react";
import ProfileButton from "@/components/pages/home/ProfileButton";

export default async function Home() {
  const session = await auth();

  const user = session?.user;
  if (!user) {
    redirect("/auth/login");
  }

  const shelves = await getShelves();

  return (
    <>
      <header className="flex justify-between items-center w-screen my-3">
        <ProfileButton firstName={user.firstName} lastName={user.lastName} />
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

            <JournalList />
          </div>
        </div>
      </main>
    </>
  );
}
