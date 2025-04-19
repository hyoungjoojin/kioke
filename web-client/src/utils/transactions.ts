import { updatePage } from "@/app/api/page";
import { groupBy } from "lodash";
import { Transaction } from "prosemirror-state";

type KiokeTransaction = {
  journalId: string;
  pageId: string;
  text: string;
};

export enum SaveStatus {
  SAVING = "saving",
  SAVED = "saved",
  ERROR = "error",
}

type StatusListener = (status: SaveStatus) => void;

export class TransactionsManager {
  private static _transactionsManager: TransactionsManager | null = null;

  private transactions: KiokeTransaction[] = [];
  private timer: NodeJS.Timeout | null = null;
  private updated: boolean = false;
  private listeners: Set<StatusListener> = new Set();

  private readonly AUTOSAVE_INTERVAL = 5 * 1000;

  constructor() {
    this.transactions = this.loadTransactionsFromLocalStorage();

    this.autoSave();
  }

  public static getTransactionsManager() {
    if (!TransactionsManager._transactionsManager) {
      TransactionsManager._transactionsManager = new TransactionsManager();
    }

    return TransactionsManager._transactionsManager;
  }

  public addTransaction(
    journalId: string,
    pageId: string,
    transaction: Transaction,
  ) {
    const kiokeTransaction =
      TransactionsManager.mapTransactionToKiokeTransaction(
        journalId,
        pageId,
        transaction,
      );

    this.transactions.push(kiokeTransaction);
    this.saveTransactionToLocalStorage(kiokeTransaction);

    if (!this.updated) {
      this.updated = true;
    }
  }

  public addListener(listener: (status: SaveStatus) => void) {
    this.listeners.add(listener);

    return () => {
      this.listeners.delete(listener);
    };
  }

  private autoSave() {
    if (this.timer) {
      clearInterval(this.timer);
    }

    this.timer = setInterval(() => {
      if (this.updated && this.transactions.length !== 0) {
        this.sendTransactionsToServer();
      }
    }, this.AUTOSAVE_INTERVAL);
  }

  private saveTransactionToLocalStorage(transaction: KiokeTransaction) {}

  private loadTransactionsFromLocalStorage(): KiokeTransaction[] {
    return [];
  }

  private async sendTransactionsToServer() {
    this.nofify(SaveStatus.SAVING);

    const groupedTransactions = groupBy(
      this.transactions,
      (value) => `${value.journalId}|${value.pageId}`,
    );

    for (const key in groupedTransactions) {
      const [journalId, pageId] = key.split("|");

      const groupedTransaction = groupedTransactions[key];
      updatePage(journalId, pageId, {
        contents: groupedTransaction[groupedTransaction.length - 1].text,
      });
    }

    this.transactions = [];
    this.updated = false;

    this.nofify(SaveStatus.SAVED);
  }

  private nofify(status: SaveStatus) {
    for (const listener of this.listeners) {
      listener(status);
    }
  }

  private static mapTransactionToKiokeTransaction(
    journalId: string,
    pageId: string,
    transaction: Transaction,
  ): KiokeTransaction {
    return {
      journalId,
      pageId,
      text: transaction.doc.textContent,
    };
  }
}
