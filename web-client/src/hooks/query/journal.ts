import {
  getJournal,
  moveJournal,
  deleteJournal,
  createJournal,
  updateJournal,
  bookmarkJournal,
  deleteBookmark,
} from "@/app/api/journal";
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

export const useCreateJournalMutation = () => {
  const queryClient = getQueryClient();

  return useMutation({
    mutationFn: (data: {
      shelfId: string;
      title: string;
      description: string;
    }) => createJournal(data.shelfId, data.title, data.description),
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ["shelves"],
      });
    },
  });
};

export const useToggleJournalBookmarkMutation = (journalId: string) => {
  const queryClient = getQueryClient();

  return useMutation({
    mutationFn: (bookmark: boolean) => {
      return bookmark ? bookmarkJournal(journalId) : deleteBookmark(journalId);
    },
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ["shelves"],
      });

      queryClient.invalidateQueries({
        queryKey: ["journals", journalId],
      });
    },
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

export const useUpdateJournalMutation = (journalId: string) => {
  const queryClient = getQueryClient();

  return useMutation({
    mutationFn: (variables: { title: string }) =>
      updateJournal(journalId, variables.title),
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ["journals", journalId],
      });

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
