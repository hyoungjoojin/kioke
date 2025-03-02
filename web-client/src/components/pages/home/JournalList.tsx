"use client";

import { getShelves } from "@/app/api/shelf";
import {
  Table,
  TableBody,
  TableHead,
  TableHeader,
  TableRow,
  TableCell,
} from "@/components/ui/table";
import { useRouter } from "next/navigation";
import { useShelvesQuery } from "@/hooks/query";
import { useSelectedShelf } from "@/hooks/store";
import { Ellipsis, Trash2 } from "lucide-react";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogTitle,
} from "@/components/ui/dialog";
import { DialogFooter, DialogHeader } from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";
import { JSX, useState } from "react";
import { getQueryClient } from "@/components/providers/QueryProvider";
import { deleteJournal } from "@/app/api/journal";

const JournalListItemMenu = ({
  jid,
  title,
}: {
  jid: string;
  title: string;
}) => {
  const queryClient = getQueryClient();

  enum JournalListItemMenuModal {
    DELETE_JOURNAL = "delete",
  }

  const [modalState, setModalState] = useState<{
    open: boolean;
    modal: JournalListItemMenuModal | null;
  }>({
    open: false,
    modal: null,
  });

  const openModal = (modal: JournalListItemMenuModal) => {
    setModalState(() => ({ open: true, modal }));
  };

  const closeModal = () => {
    setModalState(() => ({ open: false, modal: null }));
  };

  const modals: { [key in JournalListItemMenuModal]: JSX.Element } = {
    [JournalListItemMenuModal.DELETE_JOURNAL]: (
      <DialogContent onEscapeKeyDown={closeModal} hideCloseButton>
        <DialogHeader>
          <DialogTitle>Delete journal?</DialogTitle>
          <DialogDescription>
            Journal <span className="italic">{title}</span> and its pages will
            be permanently deleted.
          </DialogDescription>
        </DialogHeader>

        <DialogFooter>
          <Button onClick={closeModal}>Cancel</Button>
          <Button
            type="submit"
            variant="destructive"
            onClick={async () => {
              await deleteJournal(jid);

              queryClient.invalidateQueries({
                queryKey: ["shelves"],
                queryFn: getShelves,
              });

              closeModal();
            }}
          >
            Delete
          </Button>
        </DialogFooter>
      </DialogContent>
    ),
  };

  return (
    <Dialog open={modalState.open} onOpenChange={closeModal}>
      <DropdownMenu>
        <DropdownMenuTrigger asChild>
          <div>
            <Ellipsis size={15} />
          </div>
        </DropdownMenuTrigger>

        <DropdownMenuContent align="center">
          <DropdownMenuItem
            onSelect={() => openModal(JournalListItemMenuModal.DELETE_JOURNAL)}
          >
            <div className="flex items-center text-red-500">
              <Trash2 size={16} className="mx-1" />
              Delete Journal
            </div>
          </DropdownMenuItem>
        </DropdownMenuContent>
      </DropdownMenu>

      {modalState.open && modalState.modal && modals[modalState.modal]}
    </Dialog>
  );
};

interface JournalListItemProps {
  jid: string;
  title: string;
}

const JournalListItem = ({ jid, title }: JournalListItemProps) => {
  const router = useRouter();

  return (
    <TableRow
      changeOnHover
      onClick={() => {
        router.push(`/journal/${jid}/preview`);
      }}
    >
      <TableCell>
        <p className="select-none">{title}</p>
      </TableCell>

      <TableCell>
        <p className="select-none">{jid}</p>
      </TableCell>

      <TableCell
        onClick={(e) => {
          e.stopPropagation();
        }}
      >
        <JournalListItemMenu jid={jid} title={title} />
      </TableCell>
    </TableRow>
  );
};

export default function JournalList() {
  const { data, isLoading, isError } = useShelvesQuery();
  const selectedShelf = useSelectedShelf(data?.shelves);

  return (
    <Table>
      <TableHeader>
        <TableRow>
          <TableHead className="select-none">Title</TableHead>
          <TableHead className="select-none">Description</TableHead>
          <TableHead></TableHead>
        </TableRow>
      </TableHeader>

      <TableBody>
        {!isLoading && !isError && selectedShelf
          ? selectedShelf.journals.map((journal, index) => {
              return (
                <JournalListItem
                  key={index}
                  jid={journal.id}
                  title={journal.title}
                />
              );
            })
          : null}
      </TableBody>
    </Table>
  );
}
