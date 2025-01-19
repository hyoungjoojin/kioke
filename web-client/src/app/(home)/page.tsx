import { auth } from "@/lib/auth";
import { Avatar, AvatarFallback } from "@/components/ui/avatar";
import { redirect } from "next/navigation";

export default async function Home() {
  const session = await auth();

  const user = session?.user;
  if (!user) {
    redirect("/auth/login");
  }

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
    </>
  );
}
