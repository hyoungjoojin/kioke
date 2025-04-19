import { createPage, getPage } from "@/app/api/page";
import { getQueryClient } from "@/components/providers/QueryProvider";
import { useMutation, useQuery } from "@tanstack/react-query";

export const usePageQuery = (journalId: string, pageId: string) => {
  return useQuery({
    queryKey: ["page", journalId, pageId],
    queryFn: () => {
      return getPage(journalId, pageId);
    },
    refetchOnMount: "always",
  });
};

export const useCreatePageMutation = (journalId: string) => {
  const queryClient = getQueryClient();

  return useMutation({
    mutationFn: () => createPage(journalId),
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ["journals", journalId],
      });
    },
  });
};
