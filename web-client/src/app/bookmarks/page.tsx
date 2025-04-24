import KiokeSidebar from "@/components/features/sidebar/KiokeSidebar";
import { SidebarTrigger } from "@/components/ui/sidebar";
import { auth } from "@/lib/auth";
import { redirect } from "next/navigation";

export default async function Bookmarks() {
  const session = await auth();

  const user = session?.user;
  if (!user) {
    redirect("/auth/login");
  }

  return (
    <>
      <aside>
        <KiokeSidebar user={user} />
      </aside>

      <header className="absolute w-full pt-3 px-3">
        <SidebarTrigger />
      </header>

      <main className="w-full pt-16 px-3">
        <h1 className="pl-16 text-3xl">Bookmarks</h1>
      </main>
    </>
  );
}
