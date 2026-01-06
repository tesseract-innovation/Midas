# Guia de Solução - Problema de Build do Room

## 🔍 Diagnóstico

O erro ocorre durante o processamento de anotações do Room (kapt). Isso geralmente acontece por:
1. Incompatibilidade de versões Kotlin/Room
2. Configuração incorreta do kapt
3. Problemas com metadata do Kotlin

## ✅ Solução Rápida - Repository em Memória

Enquanto resolve o problema do Room, você pode usar uma implementação em memória que funciona perfeitamente:

### 1. Criar `InMemoryAccountRepository.kt`

```kotlin
package com.midasmoney.core.data.room.repository

import com.midasmoney.core.data.model.Account
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class InMemoryAccountRepository {

    private val _accounts = MutableStateFlow<List<Account>>(emptyList())
    val allAccounts: Flow<List<Account>> = _accounts

    suspend fun getAccountById(accountId: String): Account? {
        return _accounts.value.find { it.id.toString() == accountId }
    }

    fun getAccountByIdFlow(accountId: String): Flow<Account?> {
        return _accounts.map { accounts ->
            accounts.find { it.id.toString() == accountId }
        }
    }

    suspend fun insertAccount(account: Account): Result<Long> {
        return try {
            val currentAccounts = _accounts.value.toMutableList()
            currentAccounts.add(account)
            _accounts.value = currentAccounts
            Result.success(currentAccounts.size.toLong())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateAccount(account: Account): Result<Unit> {
        return try {
            val currentAccounts = _accounts.value.toMutableList()
            val index = currentAccounts.indexOfFirst { it.id == account.id }
            if (index != -1) {
                currentAccounts[index] = account
                _accounts.value = currentAccounts
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteAccount(account: Account): Result<Unit> {
        return try {
            val currentAccounts = _accounts.value.toMutableList()
            currentAccounts.removeIf { it.id == account.id }
            _accounts.value = currentAccounts
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteAccountById(accountId: String): Result<Unit> {
        return try {
            val currentAccounts = _accounts.value.toMutableList()
            currentAccounts.removeIf { it.id.toString() == accountId }
            _accounts.value = currentAccounts
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getAccountCount(): Int {
        return _accounts.value.size
    }

    suspend fun getTotalBalance(): Double {
        return _accounts.value.sumOf { it.balance.totalValue }
    }
}
```

### 2. Atualizar `AccountViewModelFactory.kt`

```kotlin
package com.midasmoney.screen.account.accountform

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.midasmoney.core.data.database.repository.InMemoryAccountRepository

class AccountViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    companion object {
        private val repository = InMemoryAccountRepository()
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountViewModel::class.java)) {
            return AccountViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
```

### 3. Atualizar `AccountViewModel.kt`

Trocar o tipo do repository:

```kotlin
class AccountViewModel(
    private val repository: InMemoryAccountRepository  // Mudança aqui
) : ViewModel() {
    // ... resto do código permanece igual
}
```

### 4. Remover dependência do módulo database temporariamente

Em `screen/account/build.gradle`, comente:

```gradle
dependencies {
    // ... outras dependências
    // implementation(project(':core:data:database'))  // Comentar temporariamente
}
```

## 🔧 Solução Definitiva - Corrigir Room

### Opção 1: Atualizar versões

Em `gradle/libs.versions.toml`:

```toml
[versions]
kotlin = "2.0.0"  # ou versão mais recente compatível
room = "2.6.1"
ksp = "2.0.0-1.0.21"  # Considere usar KSP em vez de kapt

[plugins]
ksp = { id = "com.google.devtools.ksp", version.ref = "ksp" }
```

### Opção 2: Usar KSP em vez de kapt

KSP é mais rápido e tem melhor suporte:

Em `core/data/database/build.gradle`:

```gradle
plugins {
    id 'midas.library'
    id 'com.google.devtools.ksp'  // Em vez de kapt
}

dependencies {
    implementation libs.androidx.room.runtime
    implementation libs.androidx.room.ktx
    ksp libs.androidx.room.compiler  // ksp em vez de kapt
}
```

### Opção 3: Verificar configuração do Kotlin

Certifique-se de que o plugin de serialização do Kotlin está configurado corretamente e não está interferindo com o kapt.

## 🧪 Testar a Implementação

Com o InMemoryRepository, você pode:

1. **Testar todas as funcionalidades do CRUD**
2. **Validar a UI e navegação**
3. **Verificar o fluxo de dados**
4. **Desenvolver outras features**

Quando o Room estiver funcionando, basta trocar de volta para `AccountRepository` e tudo continuará funcionando!

## 📊 Comparação

| Aspecto | InMemory | Room |
|---------|----------|------|
| Persistência | ❌ Dados perdidos ao fechar app | ✅ Dados salvos no SQLite |
| Performance | ✅ Muito rápido | ✅ Rápido |
| Desenvolvimento | ✅ Sem configuração | ⚠️ Requer configuração |
| Produção | ❌ Não recomendado | ✅ Recomendado |
| Testes | ✅ Perfeito para testes | ⚠️ Requer mocks |

## 🎯 Recomendação

1. **Curto prazo**: Use InMemoryRepository para continuar desenvolvendo
2. **Médio prazo**: Resolva o problema do Room para persistência real
3. **Longo prazo**: Considere adicionar sincronização com cloud

---

**Nota**: O InMemoryRepository é uma solução temporária perfeitamente válida para desenvolvimento e testes. Muitos apps começam assim antes de adicionar persistência real!
