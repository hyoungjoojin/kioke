import { auth } from "@/lib/auth";
import { redirect } from "next/navigation";

export default async function Home() {
  const session = await auth();

  const user = session?.user;
  if (!user) {
    redirect("/auth/login");
  }

  return <div>kioke.</div>;
}
