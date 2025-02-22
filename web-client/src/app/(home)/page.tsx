import { auth } from "@/lib/auth";
import { redirect } from "next/navigation";
import ShelfHeader from "@/components/pages/home/ShelfHeader";
import { getShelves } from "../api/shelf";
import JournalList from "@/components/pages/home/JournalList";
import ProfileButton from "@/components/pages/home/ProfileButton";
import {
  dehydrate,
  HydrationBoundary,
  QueryClient,
} from "@tanstack/react-query";
import AddJournalButton from "@/components/pages/home/AddJournalButton";
import ShelfListButton from "@/components/pages/home/ShelfListButton";

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
      <header className="flex justify-between items-center w-screen my-3">
        <ProfileButton firstName={user.firstName} lastName={user.lastName} />
      </header>

      <main>
        <div className="w-full flex flex-col justify-center items-center">
          <HydrationBoundary state={dehydrate(queryClient)}>
            <ShelfHeader />
          </HydrationBoundary>

          <div className="lg:w-1/2 w-4/5">
            <div className="flex w-full justify-end">
              <ShelfListButton />
              <AddJournalButton />
            </div>

            <HydrationBoundary state={dehydrate(queryClient)}>
              <JournalList />
            </HydrationBoundary>
          </div>
        </div>
      </main>
    </>
  );
}
