import { getJournal } from "@/app/api/journal";
import JournalPreviewModal from "@/components/pages/journal/preview/JournalPreviewModal";
import { QueryClient } from "@tanstack/react-query";

export default async function JournalPreview({
  params,
}: {
  params: Promise<{ jid: string }>;
}) {
  const queryClient = new QueryClient();

  const jid = (await params).jid;

  queryClient.prefetchQuery({
    queryKey: ["journals", jid],
    queryFn: () => {
      return getJournal(jid);
    },
    staleTime: 60 * 1000,
  });

  return <JournalPreviewModal jid={jid} />;
}
