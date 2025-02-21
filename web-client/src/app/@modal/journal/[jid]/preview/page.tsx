import Modal from "@/components/ui/modal";

export default async function JournalPreview({
  params,
}: {
  params: Promise<{ jid: string }>;
}) {
  const jid = (await params).jid;

  return <Modal title="temp">{jid}</Modal>;
}
