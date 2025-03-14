"use client";

import { Button } from "@/components/ui/button";
import Modal from "@/components/ui/modal";
import { useJournalQuery } from "@/hooks/query/journal";
import { useCreatePageMutation } from "@/hooks/query/page";
import { Text, Clock, SquarePen } from "lucide-react";
import { useParams, useRouter } from "next/navigation";

export default function JournalPreview() {
  const router = useRouter();
  const { journalId } = useParams<{ journalId: string }>();

  const { data: journal } = useJournalQuery(journalId);
  const { mutate: createPage } = useCreatePageMutation(journalId);

  const controls = (
    <>
      <SquarePen
        size={20}
        className="mx-2"
        onClick={() => {
          createPage();
        }}
      />
    </>
  );

  return (
    <Modal title={journal ? journal.title : "Loading..."} controls={controls}>
      <div className="flex items-center">
        <Text size={14} color="gray" />
        <span className="text-sm text-gray-500 mx-1">Description</span>
        <span>{journal?.description}</span>
      </div>
      <div className="flex items-center">
        <Clock size={14} color="gray" />
        <span className="text-sm text-gray-500 mx-1">Created At</span>
        <span>{journal?.createdAt}</span>
      </div>

      <div className="text-xl my-5">
        <h1>Pages</h1>
        {journal
          ? journal.pages.map((page, index) => {
              return (
                <div key={index}>
                  <Button
                    onClick={() => {
                      router.push(`/journal/${journalId}/${page.id}`);
                    }}
                    className="m-2"
                    variant="outline"
                  >
                    {page.date}
                  </Button>
                </div>
              );
            })
          : null}
      </div>
    </Modal>
  );
}
