"use client";

import { updatePage } from "@/app/api/page";
import { StoreApi } from "@/components/providers/StoreProvider";
import { TransactionStatus } from "@/store/transaction";
import { groupBy } from "lodash";
import { Transaction } from "prosemirror-state";

type KiokeTransaction = {
  journalId: string;
  pageId: string;
  text: string;
};

export class TransactionsManager {
  private static _transactionsManager: TransactionsManager | null = null;

  private transactions: KiokeTransaction[] = [];
  private timer: NodeJS.Timeout | null = null;
  private updated: boolean = false;

  private setStatus: ((status: TransactionStatus) => void) | null = null;

  private readonly AUTOSAVE_INTERVAL = 5 * 1000;

  constructor(store: StoreApi) {
    this.transactions = this.loadTransactionsFromLocalStorage();

    this.setStatus = store.getState().actions.setStatus;

    this.autoSave();

    TransactionsManager._transactionsManager = this;
  }

  public static getTransactionsManager() {
    if (!TransactionsManager._transactionsManager) {
      throw new Error("Transaction manager not initialized");
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

      if (this.setStatus) {
        this.setStatus(TransactionStatus.SAVING);
      }
    }
  }

  private autoSave() {
    if (this.timer) {
      clearInterval(this.timer);
    }

    this.timer = setInterval(() => {
      if (this.updated) {
        this.sendTransactionsToServer();
      }

      this.updated = false;
      if (this.setStatus) {
        this.setStatus(TransactionStatus.SAVED);
      }
    }, this.AUTOSAVE_INTERVAL);
  }

  private saveTransactionToLocalStorage(_: KiokeTransaction) {}

  private loadTransactionsFromLocalStorage(): KiokeTransaction[] {
    return [];
  }

  private async sendTransactionsToServer() {
    const groupedTransactions = groupBy(
      this.transactions,
      (value) => `${value.journalId}|${value.pageId}`,
    );

    for (const key in groupedTransactions) {
      const [journalId, pageId] = key.split("|");

      const groupedTransaction = groupedTransactions[key];
      updatePage(journalId, pageId, {
        content: groupedTransaction[groupedTransaction.length - 1].text,
      });
    }

    this.transactions = [];
    this.updated = false;
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
