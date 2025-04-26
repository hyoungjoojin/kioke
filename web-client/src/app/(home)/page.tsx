import { auth } from "@/lib/auth";
import { redirect } from "next/navigation";
import { getShelves } from "../api/shelf";
import { QueryClient } from "@tanstack/react-query";
import { SidebarTrigger } from "@/components/ui/sidebar";
import KiokeSidebar from "@/components/features/sidebar/KiokeSidebar";
import RecentlyViewedJournals from "./components/RecentlyViewedJournals";

export default async function Home() {
  const session = await auth();

  const user = session?.user;
  if (!user) {
    redirect("/auth/login");
  }

  const queryClient = new QueryClient();
  await queryClient.prefetchQuery({
    queryKey: ["shelves"],
    queryFn: getShelves,
  });

  return (
    <>
      <aside>
        <KiokeSidebar user={user} />
      </aside>

      <header className="absolute w-full pt-3 px-3">
        <SidebarTrigger />
      </header>

      <main className="w-full pt-16 px-3">
        <h1 className="pl-16 text-3xl">Welcome, {user.firstName}</h1>

        <section className="pl-16 mt-16">
          <RecentlyViewedJournals />
        </section>
      </main>
    </>
  );
}
