"use client";

import Modal from "@/components/ui/modal";
import { useJournalQuery } from "@/hooks/query";

export default function JournalPreviewModal({ jid }: { jid: string }) {
  const { data: getJournalResponseBody } = useJournalQuery(jid);

  return (
    <Modal title={getJournalResponseBody?.title}>
      {getJournalResponseBody?.jid}
    </Modal>
  );
}
