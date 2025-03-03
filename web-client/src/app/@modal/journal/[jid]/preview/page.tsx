"use client";

import Modal from "@/components/ui/modal";
import { useJournalQuery } from "@/hooks/query/journal";
import { useParams } from "next/navigation";

export default function JournalPreview() {
  const { jid } = useParams<{ jid: string }>();

  const { data } = useJournalQuery(jid);

  return (
    <Modal title={data ? data.title : "Loading..."}>
      {data ? data.jid : "Loading..."}
    </Modal>
  );
}
