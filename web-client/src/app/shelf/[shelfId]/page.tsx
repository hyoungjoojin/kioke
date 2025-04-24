"use client";

import KiokeSidebar from "@/components/features/sidebar/KiokeSidebar";
import { SidebarTrigger } from "@/components/ui/sidebar";
import { useCurrentView } from "@/hooks/store/view";
import View from "@/constants/view";
import { useSession } from "next-auth/react";
import { redirect, useParams } from "next/navigation";
import { useEffect } from "react";
import { useShelfQuery, useUpdateShelfMutation } from "@/hooks/query/shelf";
import JournalList from "./components/JournalList";
import { Skeleton } from "@/components/ui/skeleton";
import { JournalPreview } from "@/types/primitives/journal";
import EditableTitle from "@/components/features/editor/EditableTitle";
import { useSelectedShelfId } from "@/hooks/store/shelf";

function ShelfTitle(props: {
  data: {
    shelfId: string;
    name: string | undefined;
  };
  isLoading: boolean;
  isError: boolean;
}) {
  const { data, isLoading, isError } = props;

  const { mutate: updateShelf } = useUpdateShelfMutation(data.shelfId);

  if (isError) {
    return null;
  }

  if (isLoading || !data.name) {
    return <Skeleton className="px-16 w-full h-9"></Skeleton>;
  }

  return (
    <EditableTitle
      content={data.name}
      onSubmit={(content) => {
        updateShelf({ name: content });
      }}
    />
  );
}

function ShelfJournalList(props: {
  data: {
    journals: JournalPreview[] | undefined;
    shelfId: string;
    isArchived: boolean | undefined;
  };
  isLoading: boolean;
  isError: boolean;
}) {
  const { data, isLoading, isError } = props;

  if (isError) {
    return null;
  }

  if (isLoading || !data.journals || data.isArchived === undefined) {
    return <Skeleton className="px-16 w-full h-24"></Skeleton>;
  }

  return (
    <JournalList
      journals={data.journals}
      shelfId={data.shelfId}
      isArchived={data.isArchived}
    />
  );
}

export default function Shelf() {
  const { shelfId } = useParams<{ shelfId: string }>();

  const session = useSession();

  const { data: shelf, isLoading, isError } = useShelfQuery(shelfId);
  const { setSelectedShelfId } = useSelectedShelfId();
  const { setCurrentView } = useCurrentView();

  useEffect(() => {
    setSelectedShelfId(shelfId);
    setCurrentView(View.SHELF);
  }, []);

  const user = session?.data?.user;
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
        <section className="px-16 mb-10">
          <ShelfTitle
            data={{
              name: shelf?.name,
              shelfId,
            }}
            isLoading={isLoading}
            isError={isError}
          />
        </section>

        <section className="pl-16 pr-16">
          <ShelfJournalList
            data={{
              journals: shelf?.journals,
              shelfId,
              isArchived: shelf?.isArchive,
            }}
            isLoading={isLoading}
            isError={isError}
          />
        </section>
      </main>
    </>
  );
}
