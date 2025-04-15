"use client";

import { usePageQuery } from "@/hooks/query/page";
import { cn } from "@/lib/utils";
import { ArrowLeft } from "lucide-react";
import { useParams } from "next/navigation";

export default function Page() {
  const { journalId, pageId } = useParams<{
    journalId: string;
    pageId: string;
  }>();

  const { data: page } = usePageQuery(journalId, pageId);

  return (
    <>
      <header className="w-full p-10 h-24 flex justify-between items-center">
        <div className="flex items-center justify-center">
          <ArrowLeft size={15} className="mr-1" /> Back to journal
        </div>
      </header>
      <main>
        <div
          className={cn(
            "bg-white w-4/5 max-sm:w-11/12 m-auto h-full py-12 px-10",
          )}
        >
          <h1 className="text-4xl mb-5">
            {page && page.title.length === 0 ? (
              <span className="text-gray-500">Untitled</span>
            ) : (
              page?.title
            )}
          </h1>
          <p>
            {page && page.title.length === 0 ? (
              <span className="text-gray-500">Start writing your journal!</span>
            ) : (
              page?.title
            )}
          </p>
        </div>
      </main>
    </>
  );
}
