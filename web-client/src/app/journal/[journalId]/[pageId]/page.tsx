"use client";

import EditableTitle from "@/components/features/editor/EditableTitle";
import PageEditor from "@/components/features/editor/PageEditor";
import { usePageQuery, useUpdatePageMutation } from "@/hooks/query/page";
import { cn } from "@/lib/utils";
import { ArrowLeft } from "lucide-react";
import { useParams, useRouter } from "next/navigation";
import Spinner from "./components/Spinner";

export default function Page() {
  const router = useRouter();

  const { journalId, pageId } = useParams<{
    journalId: string;
    pageId: string;
  }>();

  const { data: page } = usePageQuery(journalId, pageId);
  const { mutate: updatePage } = useUpdatePageMutation(journalId, pageId);

  if (!page) {
    return null;
  }

  return (
    <>
      <header className="w-full p-10 h-24 flex justify-between items-center">
        <div
          className="flex items-center justify-center hover:cursor-pointer"
          onClick={() => {
            router.push(`/journal/${journalId}/preview`);
          }}
        >
          <ArrowLeft size={15} className="mr-1" />
          <span>Back to journal</span>
        </div>

        <Spinner />
      </header>
      <main>
        <div
          className={cn(
            "bg-white w-4/5 max-sm:w-11/12 m-auto h-full py-12 px-10",
          )}
        >
          <EditableTitle
            content={page.title.length === 0 ? "Untitled" : page.title}
            onSubmit={(title) => {
              if (title !== page.title) {
                updatePage(title);
              }
            }}
          />
          <br />

          <PageEditor
            journalId={journalId}
            pageId={pageId}
            content={page?.content}
          />
        </div>
      </main>
    </>
  );
}
