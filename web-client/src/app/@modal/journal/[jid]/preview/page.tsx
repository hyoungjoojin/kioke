"use client";

import Modal from "@/components/ui/modal";
import { useJournalQuery } from "@/hooks/query/journal";
import { Text, Clock, SquarePen } from "lucide-react";
import { useParams } from "next/navigation";

export default function JournalPreview() {
  const { jid } = useParams<{ jid: string }>();

  const { data: journal } = useJournalQuery(jid);

  const controls = (
    <>
      <SquarePen size={20} className="mx-2" />
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
    </Modal>
  );
}
