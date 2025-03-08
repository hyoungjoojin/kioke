import { getJournal, moveJournal, deleteJournal } from "@/app/api/journal";
import { getQueryClient } from "@/components/providers/QueryProvider";
import { useMutation, useQuery } from "@tanstack/react-query";

export const useJournalQuery = (jid: string) => {
  return useQuery({
    queryKey: ["journals", jid],
    queryFn: () => {
      return getJournal(jid);
    },
    staleTime: 60 * 1000,
  });
};

export const useMoveJournalMutation = (jid: string) => {
  const queryClient = getQueryClient();

  return useMutation({
    mutationFn: ({ shelfId }: { shelfId: string }) => moveJournal(jid, shelfId),
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ["shelves"],
      });
    },
  });
};

export const useDeleteJournalMutation = (jid: string) => {
  const queryClient = getQueryClient();

  return useMutation({
    mutationFn: () => deleteJournal(jid),
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ["shelves"],
      });
    },
  });
};
