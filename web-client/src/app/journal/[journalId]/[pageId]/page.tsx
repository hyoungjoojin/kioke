"use client";

import PageEditor from "@/components/ui/editor";
import { RingSpinner } from "@/components/ui/spinner";
import { usePageQuery } from "@/hooks/query/page";
import { cn } from "@/lib/utils";
import { SaveStatus, TransactionsManager } from "@/utils/transactions";
import { ArrowLeft } from "lucide-react";
import { useParams, useRouter } from "next/navigation";
import { useEffect, useState } from "react";

export default function Page() {
  const router = useRouter();

  const { journalId, pageId } = useParams<{
    journalId: string;
    pageId: string;
  }>();

  const { data: page } = usePageQuery(journalId, pageId);

  const [isSaving, setIsSaving] = useState(false);

  useEffect(() => {
    const unsubscribe =
      TransactionsManager.getTransactionsManager().addListener((status) => {
        setIsSaving(status === SaveStatus.SAVING);
      });

    return () => {
      unsubscribe();
    };
  });

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
        <RingSpinner loading={isSaving}></RingSpinner>
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
