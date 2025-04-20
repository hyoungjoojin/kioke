import { auth } from "@/lib/auth";
import { redirect } from "next/navigation";
import ShelfHeader from "./components/ShelfHeader";
import { getShelves } from "../api/shelf";
import JournalList from "./components/JournalList";
import ProfileButton from "./components/ProfileButton";
import {
  dehydrate,
  HydrationBoundary,
  QueryClient,
} from "@tanstack/react-query";
import AddJournalButton from "./components/AddJournalButton";
import ShowShelfListButton from "./components/ShowShelfListButton";

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
              <ShowShelfListButton className="mx-2" />
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
